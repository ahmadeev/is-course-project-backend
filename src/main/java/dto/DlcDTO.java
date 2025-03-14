package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Dlc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DlcDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private List<SurvivorDTO> survivors; // Полные объекты SurvivorDTO
    private List<KillerDTO> killers;     // Полные объекты KillerDTO

    public static DlcDTO fromEntity(Dlc dlc) {
        List<SurvivorDTO> survivorDTOs = dlc.getSurvivors().stream()
                .map(SurvivorDTO::fromEntity)
                .collect(Collectors.toList());
        List<KillerDTO> killerDTOs = dlc.getKillers().stream()
                .map(KillerDTO::fromEntity)
                .collect(Collectors.toList());

        return new DlcDTO(
                dlc.getId(),
                dlc.getName(),
                dlc.getDescription(),
                dlc.getReleaseDate(),
                survivorDTOs,
                killerDTOs
        );
    }

    public Dlc toEntity() {
        Dlc dlc = new Dlc();
        dlc.setId(this.id);
        dlc.setName(this.name);
        dlc.setDescription(this.description);
        dlc.setReleaseDate(this.releaseDate);
        if (this.survivors != null) {
            dlc.setSurvivors(this.survivors.stream().map(SurvivorDTO::toEntity).collect(Collectors.toList()));
        }
        if (this.killers != null) {
            dlc.setKillers(this.killers.stream().map(KillerDTO::toEntity).collect(Collectors.toList()));
        }
        return dlc;
    }
}
