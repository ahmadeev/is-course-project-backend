package _service.build;

import _repository.build.SurvivorBuildRepository;
import _repository._utils.UserSurvivorBuildRatingRepository;
import _service.BaseService;
import init.GlobalState;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import model._utils.User;
import model._utils.rating.UserSurvivorBuildRating;
import utils.Utility;

import java.util.List;
import java.util.Optional;

@Stateless
public class SurvivorBuildService extends BaseService<SurvivorBuild, Long> {
    @EJB
    private SurvivorBuildRepository repository;

    @EJB
    private UserSurvivorBuildRatingRepository ratingRepository;

    @EJB
    private GlobalState globalState;

    @EJB
    private Utility utility;

    @Override
    protected SurvivorBuildRepository getRepository() {
        return repository;
    }

    public SurvivorBuild generateRandomSurvivorBuild() {
        return utility.generateRandomSurvivorBuild(globalState.getSurvivorPerks());
    }

    @TransactionAttribute
    public SurvivorBuild approveSurvivorBuild(long buildId, boolean approved) {
        SurvivorBuild build = repository.findById(buildId);
        if (build == null) {
            throw new RuntimeException();
        }
        build.setApprovedByAdmin(approved);
        return repository.update(build);
    }

    @TransactionAttribute
    public List<UserSurvivorBuildRating> getRatedBuilds(User user) {
        return ratingRepository.findByUser(user);
    }

    @TransactionAttribute
    public void updateSurvivorBuildRating(User user, SurvivorBuild build, int rating) {
        UserSurvivorBuildRating userSurvivorBuildRating = ratingRepository.findByUserAndBuild(user, build);
        if (userSurvivorBuildRating == null) {
            userSurvivorBuildRating = new UserSurvivorBuildRating();
        }
        userSurvivorBuildRating.setUser(user);
        userSurvivorBuildRating.setBuild(build);
        userSurvivorBuildRating.setRating(rating);
        ratingRepository.update(userSurvivorBuildRating);
    }

    // ------

    // 1. Случайный билд из лучших по rating (топ-10)
    public SurvivorBuild findRandomTopRatedBuild() {
        Optional<SurvivorBuild> build = repository.findRandomTopRatedBuild();
        return build.orElseThrow(() -> new RuntimeException("No top-rated builds found"));
    }

    // 2. Случайный билд из самых популярных по usageCount (топ-10)
    public SurvivorBuild findRandomMostPopularBuild() {
        Optional<SurvivorBuild> build = repository.findRandomMostPopularBuild();
        return build.orElseThrow(() -> new RuntimeException("No popular builds found"));
    }

    // 3. Случайный билд с approvedByAdmin == true
    public SurvivorBuild findRandomApprovedBuild() {
        Optional<SurvivorBuild> build = repository.findRandomApprovedBuild();
        return build.orElseThrow(() -> new RuntimeException("No approved builds found"));
    }
}
