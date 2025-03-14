package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Survivor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivorDTO {
    private Long id;
    private String name;
    private String lore;
    private List<SurvivorPerkDTO> perks; // Полные объекты SurvivorPerkDTO
    private Long dlcId;                  // Только ID для избежания цикла

    public static SurvivorDTO fromEntity(Survivor survivor) {
        List<SurvivorPerkDTO> perkDTOs = survivor.getPerks().stream()
                .map(SurvivorPerkDTO::fromEntity)
                .collect(Collectors.toList());

        return new SurvivorDTO(
                survivor.getId(),
                survivor.getName(),
                survivor.getLore(),
                perkDTOs,
                survivor.getDlc() != null ? survivor.getDlc().getId() : null
        );
    }

    public Survivor toEntity() {
        Survivor survivor = new Survivor();
        survivor.setId(this.id);
        survivor.setName(this.name);
        survivor.setLore(this.lore);
        if (this.perks != null) {
            survivor.setPerks(this.perks.stream().map(SurvivorPerkDTO::toEntity).collect(Collectors.toList()));
        }
        // dlc устанавливается отдельно через сервис
        return survivor;
    }
}
