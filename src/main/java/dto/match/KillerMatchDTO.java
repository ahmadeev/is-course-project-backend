package dto.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto._utils.UserDTO;
import dto.build.KillerBuildDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.match.KillerMatch;

import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KillerMatchDTO {
    private Long id;
    private KillerBuildDTO build;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO user;
    private boolean won;
    private String createdAt;
    private String updatedAt;

    public static KillerMatchDTO fromEntity(KillerMatch match) {
        if (match == null) {
            return null;
        }

        KillerBuildDTO buildDTO = match.getBuild() != null ? KillerBuildDTO.fromEntity(match.getBuild()) : null;
        UserDTO userDTO = match.getUser() != null ? UserDTO.fromEntity(match.getUser()) : null;

        return new KillerMatchDTO(
                match.getId(),
                buildDTO,
                userDTO,
                match.isWon(),
                match.getCreatedAt(),
                match.getUpdatedAt()
        );
    }

    public KillerMatch toEntity() {
        KillerMatch match = new KillerMatch();
        match.setId(this.id);
        match.setBuild(this.build != null ? this.build.toEntity() : null);
        match.setUser(this.user != null ? this.user.toEntity() : null);
        match.setWon(this.won);
        match.setCreatedAt(this.createdAt);
        match.setUpdatedAt(this.updatedAt);
        return match;
    }
}
