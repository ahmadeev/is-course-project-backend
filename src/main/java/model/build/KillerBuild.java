package model.build;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.match.KillerMatch;
import model.perk.KillerPerk;
import model._utils.BaseEntity;
import model._utils.Build;
import model._utils.User;
import model._utils.rating.UserKillerBuildRating;
import model.tag.KillerBuildTag;

import java.util.*;

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
    private double rating;

    @Column(name = "approved_by_admin")
    private boolean approvedByAdmin;

    // ------

    @ManyToMany(mappedBy = "favoriteKillerBuilds", fetch = FetchType.LAZY)
    private List<User> favoritedByUsers = new ArrayList<>();

    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL)
    private List<UserKillerBuildRating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "build", fetch = FetchType.LAZY)
    private List<KillerMatch> matches = new ArrayList<>();

    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL)
    private List<KillerBuildTag> tags = new ArrayList<>();

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

    // ------

    public void addKillerBuildTag(KillerBuildTag tag) {
        tags.add(tag);
    }

    public void removeKillerBuildTag(KillerBuildTag tag) {
        tags.remove(tag);
    }
}
