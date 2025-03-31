package auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model._utils.Role;

@Entity
@Table(name = "app_admin_requests")
@Getter
@Setter
@NoArgsConstructor
public class AdminRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Поле name не должно быть пустым")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotNull(message = "Поле password не должно быть пустым")
    @Column(name = "password")
    private String password;

    public AdminRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

