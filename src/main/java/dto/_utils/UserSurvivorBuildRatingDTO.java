package dto._utils;

import lombok.Getter;
import lombok.Setter;
import model._utils.rating.UserSurvivorBuildRating;

@Getter
@Setter
public class UserSurvivorBuildRatingDTO {
    private Long id;
    private Long userId;
    private Long buildId;
    private int rating;

    public static UserSurvivorBuildRatingDTO fromEntity(UserSurvivorBuildRating entity) {
        if (entity == null) {
            return null;
        }
        UserSurvivorBuildRatingDTO dto = new UserSurvivorBuildRatingDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setBuildId(entity.getBuild().getId());
        dto.setRating(entity.getRating());
        return dto;
    }
}
