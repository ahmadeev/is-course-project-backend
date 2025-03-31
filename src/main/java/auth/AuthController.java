package auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model._utils.Role;
import model._utils.User;
import org.mindrot.jbcrypt.BCrypt;
import response.ResponseStatus;
import responses.ResponseEntity;

import java.util.*;

@Named(value = "authController")
@ApplicationScoped
@Path("/auth")
public class AuthController {

    private final static String SECRET_KEY = "secret_key";

    @Inject
    private AuthService authService;

    @PostConstruct
    private void init() {
        System.out.println("AuthController initialized");
    }

    @POST
    @Path("/sign-in")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(@Valid UserDTO userDTO) {
        System.out.println("User is trying to sign in");

        User userInput = authService.createEntityFromDTO(userDTO);
        User userStored = authService.getUserByName(userInput.getUsername());

        if (userStored == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ResponseEntity(ResponseStatus.ERROR,"User does not exist", null)
            ).build();
        }

        // TODO: стоит добавлять в JWT еще и id
        if (BCrypt.checkpw(userInput.getPassword(), userStored.getPassword())) {
            String token = JWT.create()
                    .withSubject(userStored.getUsername())
                    .withClaim("roles", userStored.getRoles().toString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3_600_000)) // 1 час = 3_600_000, 1 минута = 60_000
                    .sign(Algorithm.HMAC256(SECRET_KEY));

            System.out.println("User successfully signed in");
            List<Role> roles = new LinkedList<>(userStored.getRoles());
            return Response.ok(
                    new ResponseEntity(ResponseStatus.SUCCESS,"User successfully signed in", new SignInResponse(token, roles))
            ).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ResponseEntity(ResponseStatus.ERROR,"Password is incorrect", null)
            ).build();
        }
    }

    @POST
    @Path("/sign-up")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(@Valid UserDTO userDTO) {
        System.out.println("User is trying to sign up");

        User userInput = authService.createEntityFromDTO(userDTO);
        User userStored = authService.getUserByName(userInput.getUsername());

        if (userStored != null) {
            System.out.println("User already exists");
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ResponseEntity(ResponseStatus.ERROR,"User already exists", null)
            ).build();
        }

        String msg = authService.createUser(userInput);
        System.out.println("User successfully signed up");
        return Response.ok().entity(
                new ResponseEntity(ResponseStatus.SUCCESS, msg, null)
        ).build();
    }
}