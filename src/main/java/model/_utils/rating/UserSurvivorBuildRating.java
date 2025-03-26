package model._utils.rating;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model._utils.User;
import model.build.SurvivorBuild;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user_survivor_build_rating",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "build_id"}
        ))
public class UserSurvivorBuildRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "build_id")
    private SurvivorBuild build;

    @Column(name = "rating")
    private Integer rating;
}
