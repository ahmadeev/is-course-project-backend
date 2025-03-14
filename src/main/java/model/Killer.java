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
public class Killer {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String lore;

    @OneToMany(mappedBy = "killer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KillerPerk> perks = new ArrayList<>();

    @OneToMany(mappedBy = "killer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addon> addon = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dlc_id")
    private Dlc dlc;
}
