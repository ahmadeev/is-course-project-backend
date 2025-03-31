package auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import model._utils.Role;
import response.ResponseStatus;
import responses.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Provider  // аннотация, чтобы зарегистрировать фильтр как ресурс
@Priority(2)  // фильтр с наибольшим приоритетом
public class AuthFilter implements ContainerRequestFilter {

    private final static String SECRET_KEY = "secret_key";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // не учитывается начало эндпоинта api (одинаков для всего приложения)
        String path = requestContext.getUriInfo().getPath();
        System.out.println(path);

        if (!(path.startsWith("/auth") || path.startsWith("/dlc"))) {
            // извлекаем токен из заголовка Authorization
            System.out.println("Verifying token");
            String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // проверяем, есть ли токен в заголовке и начинается ли он с "Bearer"
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Request denied");
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity(new ResponseEntity(ResponseStatus.ERROR, "Authorization token is missing or malformed", null))
                                .build()
                );
                return;
            }

            // извлекаем сам токен (без "Bearer " префикса)
            String token = authHeader.substring(7);

            try {
                // декодируем и проверяем токен
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                        .build()
                        .verify(token);

                System.out.println("Valid token");

                // если токен валидный, извлекаем информацию, например, имя пользователя
                String username = decodedJWT.getSubject();

                Long userId = decodedJWT.getClaim("id").asLong();

                // извлечение ролей
                List<Role> roles = parseRolesFromJwt(decodedJWT.getClaim("roles").asString());

                // Устанавливаем кастомный SecurityContext
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return new UserPrincipal(username, userId);
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return roles != null && roles.contains(Role.valueOf(role));
                    }

                    @Override
                    public boolean isSecure() {
                        return requestContext.getUriInfo().getAbsolutePath().toString().startsWith("https");
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "Bearer";
                    }
                });

                if (path.matches("^/build/(survivor|killer)/([\\w-]+)/approve$") && !requestContext.getSecurityContext().isUserInRole(Role.ROLE_ADMIN.toString())) {
                    System.out.println("Request denied. User does not have admin privilege");
                    requestContext.abortWith(
                            Response.status(Response.Status.UNAUTHORIZED)
                                    .entity(new ResponseEntity(ResponseStatus.ERROR, "User does not have admin privilege", null))
                                    .build()
                    );
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Invalid token");

                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity(new ResponseEntity(ResponseStatus.ERROR, "Invalid or expired token", null))
                                .build()
                );
            }
        } else {
            System.out.println("Token does not have to be verified");
        }
    }

    public List<Role> parseRolesFromJwt(String rolesClaim) {
        if (rolesClaim == null || rolesClaim.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String cleanedRoles = rolesClaim
                .replace("[", "")
                .replace("]", "")
                .trim();

        if (cleanedRoles.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(cleanedRoles.split(","))
                .map(String::trim)
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }
}

