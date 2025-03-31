package dto._utils;

import lombok.Getter;
import lombok.Setter;
import model._utils.rating.UserKillerBuildRating;

@Getter
@Setter
public class UserKillerBuildRatingDTO {
    private Long id;
    private Long userId;
    private Long buildId;
    private int rating;

    public static UserKillerBuildRatingDTO fromEntity(UserKillerBuildRating entity) {
        if (entity == null) {
            return null;
        }
        UserKillerBuildRatingDTO dto = new UserKillerBuildRatingDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setBuildId(entity.getBuild().getId());
        dto.setRating(entity.getRating());
        return dto;
    }
}
