package dto.perk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.perk.KillerPerk;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KillerPerkDTO {
    private Long id;
    private String name;
    private String description;
    private Long killerId; // Только ID для избежания цикла

    public static KillerPerkDTO fromEntity(KillerPerk perk) {
        return new KillerPerkDTO(
                perk.getId(),
                perk.getName(),
                perk.getDescription(),
                perk.getKiller() != null ? perk.getKiller().getId() : null
        );
    }

    public KillerPerk toEntity() {
        KillerPerk perk = new KillerPerk();
        perk.setId(this.id);
        perk.setName(this.name);
        perk.setDescription(this.description);
        // killer устанавливается отдельно через сервис
        return perk;
    }
}
