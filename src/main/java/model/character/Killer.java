package model.character;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Addon;
import model.Dlc;
import model.perk.KillerPerk;
import model._utils.Character;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "killer")
public class Killer implements Character {
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
