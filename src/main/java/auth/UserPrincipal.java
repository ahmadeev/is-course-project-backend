package auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
public class UserPrincipal implements Principal {
    private final String username;
    private final Long userId;

    @Override
    public String getName() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }
}