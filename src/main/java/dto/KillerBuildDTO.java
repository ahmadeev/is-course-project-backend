package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.KillerBuild;
import model.KillerPerk;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KillerBuildDTO {
    private Long id;
    private List<KillerPerkDTO> perks;
    private long usageCount = 1;
    private double rating = 10;
    private boolean approvedByAdmin;

    public static KillerBuildDTO fromEntity(KillerBuild build) {
        return new KillerBuildDTO(
                build.getId(),
                build.getPerks().stream()
                        .map(KillerPerkDTO::fromEntity)
                        .collect(Collectors.toList()),
                build.getUsageCount(),
                build.getRating(),
                build.isApprovedByAdmin()
        );
    }

    public KillerBuild toEntity() {
        KillerBuild build = new KillerBuild();
        build.setId(this.id);
        // perks устанавливаются отдельно через сервис
        return build;
    }
}

