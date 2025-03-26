package model.build;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model._utils.rating.UserSurvivorBuildRating;
import model.match.KillerMatch;
import model.match.SurvivorMatch;
import model.perk.SurvivorPerk;
import model._utils.*;
import model.tag.KillerBuildTag;
import model.tag.SurvivorBuildTag;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "survivor_build")
public class SurvivorBuild extends BaseEntity implements Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "survivor_build_perks",
            joinColumns = @JoinColumn(name = "build_id"),
            inverseJoinColumns = @JoinColumn(name = "perk_id")
    )
    @Size(min = 4, max = 4, message = "SurvivorBuild must have exactly 4 perks")
    private List<SurvivorPerk> perks = new ArrayList<>();

    // не относится к модели

    @Column(name = "usage_count")
    private long usageCount = 1;

    @Column(name = "rating")
    private double rating;

    @Column(name = "approved_by_admin")
    private boolean approvedByAdmin;

    // ------

    @ManyToMany(mappedBy = "favoriteSurvivorBuilds", fetch = FetchType.LAZY)
    private List<User> favoritedByUsers = new ArrayList<>();

    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL)
    private List<UserSurvivorBuildRating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "build", fetch = FetchType.LAZY)
    private List<SurvivorMatch> matches = new ArrayList<>();

    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL)
    private List<SurvivorBuildTag> tags = new ArrayList<>();

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

    public void addSurvivorBuildTag(SurvivorBuildTag tag) {
        tags.add(tag);
    }

    public void removeSurvivorBuildTag(SurvivorBuildTag tag) {
        tags.remove(tag);
    }
}
