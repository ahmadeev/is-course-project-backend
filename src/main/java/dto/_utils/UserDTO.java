package dto._utils;

import dto.build.KillerBuildDTO;
import dto.build.SurvivorBuildDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import model._utils.Role;
import model._utils.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private List<SurvivorBuildDTO> favoriteSurvivorBuilds;
    private List<KillerBuildDTO> favoriteKillerBuilds;
    private String createdAt;
    private String updatedAt;

    public static UserDTO fromEntity(User user) {
        List<SurvivorBuildDTO> survivorBuildDTOs = user.getFavoriteSurvivorBuilds().stream()
                .map(SurvivorBuildDTO::fromEntity)
                .collect(Collectors.toList());

        List<KillerBuildDTO> killerBuildDTOs = user.getFavoriteKillerBuilds().stream()
                .map(KillerBuildDTO::fromEntity)
                .collect(Collectors.toList());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles(),
                survivorBuildDTOs,
                killerBuildDTOs,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRoles(this.roles);
        if (this.favoriteSurvivorBuilds != null) {
            user.setFavoriteSurvivorBuilds(
                    this.favoriteSurvivorBuilds.stream()
                            .map(SurvivorBuildDTO::toEntity)
                            .collect(Collectors.toList())
            );
        } else {
            user.setFavoriteSurvivorBuilds(new ArrayList<>());
        }
        if (this.favoriteKillerBuilds != null) {
            user.setFavoriteKillerBuilds(
                    this.favoriteKillerBuilds.stream()
                            .map(KillerBuildDTO::toEntity)
                            .collect(Collectors.toList())
            );
        } else {
            user.setFavoriteKillerBuilds(new ArrayList<>());
        }
        user.setCreatedAt(this.createdAt != null ? this.createdAt : new Date().toString());
        user.setUpdatedAt(this.updatedAt != null ? this.updatedAt : new Date().toString());
        return user;
    }
}
