package dto;

import dto.character.KillerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Addon;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddonDTO {
    private Long id;
    private String name;
    private String description;
    private KillerDTO killer; // Полный объект KillerDTO

    public static AddonDTO fromEntity(Addon addon) {
        return new AddonDTO(
                addon.getId(),
                addon.getName(),
                addon.getDescription(),
                addon.getKiller() != null ? KillerDTO.fromEntity(addon.getKiller()) : null
        );
    }

    public Addon toEntity() {
        Addon addon = new Addon();
        addon.setId(this.id);
        addon.setName(this.name);
        addon.setDescription(this.description);
        if (this.killer != null) {
            addon.setKiller(this.killer.toEntity());
        }
        return addon;
    }
}
