package dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto._utils.UserDTO;
import dto.build.KillerBuildDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.match.KillerMatch;
import model.tag.KillerBuildTag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KillerBuildTagDTO {
    private Long id;
    private KillerBuildDTO build;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO user;
    private String tag;
    private String createdAt;
    private String updatedAt;

    public static KillerBuildTagDTO fromEntity(KillerBuildTag tag) {
        if (tag == null) {
            return null;
        }

        KillerBuildDTO buildDTO = tag.getBuild() != null ? KillerBuildDTO.fromEntity(tag.getBuild()) : null;
        UserDTO userDTO = tag.getUser() != null ? UserDTO.fromEntity(tag.getUser()) : null;

        return new KillerBuildTagDTO(
                tag.getId(),
                buildDTO,
                userDTO,
                tag.getTag(),
                tag.getCreatedAt(),
                tag.getUpdatedAt()
        );
    }

    public KillerBuildTag toEntity() {
        KillerBuildTag tag = new KillerBuildTag();
        tag.setId(this.id);
        tag.setBuild(this.build != null ? this.build.toEntity() : null);
        tag.setUser(this.user != null ? this.user.toEntity() : null);
        tag.setTag(this.tag);
        tag.setCreatedAt(this.createdAt);
        tag.setUpdatedAt(this.updatedAt);
        return tag;
    }
}
