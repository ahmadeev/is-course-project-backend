package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.SurvivorBuild;
import model.SurvivorPerk;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivorBuildDTO {
    private Long id;
    private List<SurvivorPerkDTO> perks; // Полные объекты Perk вместо ID

    public static SurvivorBuildDTO fromEntity(SurvivorBuild build) {
        return new SurvivorBuildDTO(
                build.getId(),
                build.getPerks().stream()
                        .map(SurvivorPerkDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public SurvivorBuild toEntity() {
        SurvivorBuild build = new SurvivorBuild();
        build.setId(this.id);
        // perks заполняются отдельно через сервис
        return build;
    }
}


