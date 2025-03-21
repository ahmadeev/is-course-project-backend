package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.utils.BaseEntity;
import model.utils.Build;
import model.utils.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "killer_build")
public class KillerBuild extends BaseEntity implements Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "killer_build_perks",
            joinColumns = @JoinColumn(name = "build_id"),
            inverseJoinColumns = @JoinColumn(name = "perk_id")
    )
    @Size(min = 4, max = 4, message = "KillerBuild must have exactly 4 perks")
    private List<KillerPerk> perks = new ArrayList<>();

    // не относится к модели

    @Column(name = "usage_count")
    private long usageCount = 1;

    @Column(name = "rating")
    private double rating = 10;

    @Column(name = "approved_by_admin")
    private boolean approvedByAdmin;

    // ------

    @ManyToMany(mappedBy = "favoriteKillerBuilds", fetch = FetchType.LAZY)
    private List<User> favoritedByUsers = new ArrayList<>();

    // ------

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date().toString());
        this.setUpdatedAt(new Date().toString());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(new Date().toString());
    }
}
