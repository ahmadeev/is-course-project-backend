package _service.build;

import _repository.build.SurvivorBuildRepository;
import _repository._utils.UserSurvivorBuildRatingRepository;
import _service.BaseService;
import init.GlobalState;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.build.SurvivorBuild;
import model._utils.User;
import model._utils.rating.UserSurvivorBuildRating;
import utils.Utility;

import java.util.List;

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
}
