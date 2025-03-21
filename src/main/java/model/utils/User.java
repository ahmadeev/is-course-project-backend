package model.utils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_survivor_build",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "survivor_build_id")
    )
    List<SurvivorBuild> favoriteSurvivorBuilds = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_killer_build",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "killer_build_id")
    )
    List<KillerBuild> favoriteKillerBuilds = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date().toString());
        this.setUpdatedAt(new Date().toString());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(new Date().toString());
    }

    // вспомогательные (не касаются сущности напрямую)

    // учитывают и обратную связь в SurvivorBuild
    public void addFavoriteSurvivorBuild(SurvivorBuild build) {
        favoriteSurvivorBuilds.add(build);
        build.getFavoritedByUsers().add(this);
    }

    public void removeFavoriteSurvivorBuild(SurvivorBuild build) {
        favoriteSurvivorBuilds.remove(build);
        build.getFavoritedByUsers().remove(this);
    }

    public void addFavoriteKillerBuild(KillerBuild build) {
        favoriteKillerBuilds.add(build);
        build.getFavoritedByUsers().add(this);
    }

    public void removeFavoriteKillerBuild(KillerBuild build) {
        favoriteKillerBuilds.remove(build);
        build.getFavoritedByUsers().remove(this);
    }
}
