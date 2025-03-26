package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.character.Killer;
import model.character.Survivor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "dlc")
public class Dlc {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "dlc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survivor> survivors = new ArrayList<>();

    @OneToMany(mappedBy = "dlc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Killer> killers = new ArrayList<>();
}
