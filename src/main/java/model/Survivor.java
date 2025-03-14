package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Survivor {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String lore;

    @OneToMany(mappedBy = "survivor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurvivorPerk> perks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dlc_id")
    private Dlc dlc;
}
