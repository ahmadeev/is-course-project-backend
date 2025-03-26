package dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto._utils.UserDTO;
import dto.build.SurvivorBuildDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.tag.SurvivorBuildTag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurvivorBuildTagDTO {
    private Long id;
    private SurvivorBuildDTO build;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO user;
    private String tag;
    private String createdAt;
    private String updatedAt;

    public static SurvivorBuildTagDTO fromEntity(SurvivorBuildTag tag) {
        if (tag == null) {
            return null;
        }

        SurvivorBuildDTO buildDTO = tag.getBuild() != null ? SurvivorBuildDTO.fromEntity(tag.getBuild()) : null;
        UserDTO userDTO = tag.getUser() != null ? UserDTO.fromEntity(tag.getUser()) : null;

        return new SurvivorBuildTagDTO(
                tag.getId(),
                buildDTO,
                userDTO,
                tag.getTag(),
                tag.getCreatedAt(),
                tag.getUpdatedAt()
        );
    }

    public SurvivorBuildTag toEntity() {
        SurvivorBuildTag tag = new SurvivorBuildTag();
        tag.setId(this.id);
        tag.setBuild(this.build != null ? this.build.toEntity() : null);
        tag.setUser(this.user != null ? this.user.toEntity() : null);
        tag.setTag(this.tag);
        tag.setCreatedAt(this.createdAt);
        tag.setUpdatedAt(this.updatedAt);
        return tag;
    }
}
