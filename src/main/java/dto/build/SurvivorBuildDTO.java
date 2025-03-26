package dto.build;

import dto.perk.SurvivorPerkDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.build.SurvivorBuild;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivorBuildDTO {
    private Long id;
    private List<SurvivorPerkDTO> perks;
    private long usageCount = 1;
    private double rating = 10;
    private boolean approvedByAdmin;

    public static SurvivorBuildDTO fromEntity(SurvivorBuild build) {
        return new SurvivorBuildDTO(
                build.getId(),
                build.getPerks().stream()
                        .map(SurvivorPerkDTO::fromEntity)
                        .collect(Collectors.toList()),
                build.getUsageCount(),
                build.getRating(),
                build.isApprovedByAdmin()
        );
    }

    public SurvivorBuild toEntity() {
        SurvivorBuild build = new SurvivorBuild();
        build.setId(this.id);
        // perks заполняются отдельно через сервис
        return build;
    }
}


