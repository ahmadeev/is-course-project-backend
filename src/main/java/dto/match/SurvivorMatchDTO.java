package dto.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto._utils.UserDTO;
import dto.build.SurvivorBuildDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.match.SurvivorMatch;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivorMatchDTO {
    private Long id;
    private SurvivorBuildDTO build;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO user;
    private boolean won;
    private String createdAt;
    private String updatedAt;

    public static SurvivorMatchDTO fromEntity(SurvivorMatch match) {
        if (match == null) {
            return null;
        }

        SurvivorBuildDTO buildDTO = match.getBuild() != null ? SurvivorBuildDTO.fromEntity(match.getBuild()) : null;
        UserDTO userDTO = match.getUser() != null ? UserDTO.fromEntity(match.getUser()) : null;

        return new SurvivorMatchDTO(
                match.getId(),
                buildDTO,
                userDTO,
                match.isWon(),
                match.getCreatedAt(),
                match.getUpdatedAt()
        );
    }

    public SurvivorMatch toEntity() {
        SurvivorMatch match = new SurvivorMatch();
        match.setId(this.id);
        match.setBuild(this.build != null ? this.build.toEntity() : null);
        match.setUser(this.user != null ? this.user.toEntity() : null);
        match.setWon(this.won);
        match.setCreatedAt(this.createdAt);
        match.setUpdatedAt(this.updatedAt);
        return match;
    }
}
