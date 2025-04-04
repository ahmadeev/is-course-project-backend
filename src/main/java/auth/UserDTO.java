package auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "Поле name не должно быть пустым")
    @NotEmpty
    private String name;

    @NotNull(message = "Поле password не должно быть пустым")
    @NotEmpty
    private String password;

    private String isAdmin = "false";
}
