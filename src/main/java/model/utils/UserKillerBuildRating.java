package model.utils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.KillerBuild;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user_killer_build_rating",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "build_id"}
        ))
public class UserKillerBuildRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "build_id")
    private KillerBuild build;

    @Column(name = "rating")
    private Integer rating;
}
