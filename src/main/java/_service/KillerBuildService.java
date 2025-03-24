package _service;

import _repository.KillerBuildRepository;
import _repository.utils.UserKillerBuildRatingRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.KillerBuild;
import model.KillerPerk;
import model.SurvivorBuild;
import model.utils.User;
import model.utils.UserKillerBuildRating;
import utils.Utility;

import java.util.List;

@Stateless
public class KillerBuildService extends BaseService<KillerBuild, Long> {
    @EJB
    private KillerBuildRepository repository;

    @EJB
    private UserKillerBuildRatingRepository ratingRepository;

    @EJB
    private Utility utility;

    @Override
    protected KillerBuildRepository getRepository() {
        return repository;
    }

    public KillerBuild generateRandomKillerBuild(List<KillerPerk> perks) {
        return utility.generateRandomKillerBuild(perks);
    }

    @TransactionAttribute
    public KillerBuild approveKillerBuild(long buildId, boolean approved) {
        KillerBuild build = repository.findById(buildId);
        if (build == null) {
            throw new RuntimeException();
        }
        build.setApprovedByAdmin(approved);
        return repository.update(build);
    }

    @TransactionAttribute
    public List<UserKillerBuildRating> getRatedBuilds(User user) {
        return ratingRepository.findByUser(user);
    }

    @TransactionAttribute
    public void updateKillerBuildRating(User user, KillerBuild build, int rating) {
        UserKillerBuildRating userKillerBuildRating = ratingRepository.findByUserAndBuild(user, build);
        if (userKillerBuildRating == null) {
            userKillerBuildRating = new UserKillerBuildRating();
        }
        userKillerBuildRating.setUser(user);
        userKillerBuildRating.setBuild(build);
        userKillerBuildRating.setRating(rating);
        ratingRepository.update(userKillerBuildRating);
    }
}
