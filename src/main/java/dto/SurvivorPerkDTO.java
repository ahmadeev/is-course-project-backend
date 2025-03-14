package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.SurvivorPerk;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivorPerkDTO {
    private Long id;
    private String name;
    private String description;
    private Long survivorId; // Только ID для избежания цикла

    public static SurvivorPerkDTO fromEntity(SurvivorPerk perk) {
        return new SurvivorPerkDTO(
                perk.getId(),
                perk.getName(),
                perk.getDescription(),
                perk.getSurvivor() != null ? perk.getSurvivor().getId() : null
        );
    }

    public SurvivorPerk toEntity() {
        SurvivorPerk perk = new SurvivorPerk();
        perk.setId(this.id);
        perk.setName(this.name);
        perk.setDescription(this.description);
        // survivor устанавливается отдельно через сервис
        return perk;
    }
}
