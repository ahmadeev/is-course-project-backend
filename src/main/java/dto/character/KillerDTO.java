package dto.character;

import dto.AddonDTO;
import dto.perk.KillerPerkDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.character.Killer;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KillerDTO {
    private Long id;
    private String name;
    private String lore;
    private List<KillerPerkDTO> perks; // Полные объекты KillerPerkDTO
    private List<AddonDTO> addons;    // Полные объекты AddonDTO (исправлено с addon)
    private Long dlcId;               // Только ID для избежания цикла

    public static KillerDTO fromEntity(Killer killer) {
        List<KillerPerkDTO> perkDTOs = killer.getPerks().stream()
                .map(KillerPerkDTO::fromEntity)
                .collect(Collectors.toList());
        List<AddonDTO> addonDTOs = killer.getAddon().stream()
                .map(AddonDTO::fromEntity)
                .collect(Collectors.toList());

        return new KillerDTO(
                killer.getId(),
                killer.getName(),
                killer.getLore(),
                perkDTOs,
                addonDTOs,
                killer.getDlc() != null ? killer.getDlc().getId() : null
        );
    }

    public Killer toEntity() {
        Killer killer = new Killer();
        killer.setId(this.id);
        killer.setName(this.name);
        killer.setLore(this.lore);
        if (this.perks != null) {
            killer.setPerks(this.perks.stream().map(KillerPerkDTO::toEntity).collect(Collectors.toList()));
        }
        if (this.addons != null) {
            killer.setAddon(this.addons.stream().map(AddonDTO::toEntity).collect(Collectors.toList()));
        }
        // dlc устанавливается отдельно через сервис
        return killer;
    }
}
