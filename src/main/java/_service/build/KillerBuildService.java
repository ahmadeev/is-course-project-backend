package _service.build;

import _repository.build.KillerBuildRepository;
import _repository._utils.UserKillerBuildRatingRepository;
import _service.BaseService;
import init.GlobalState;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.Query;
import model.build.KillerBuild;
import model._utils.User;
import model._utils.rating.UserKillerBuildRating;
import org.hibernate.Hibernate;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class KillerBuildService extends BaseService<KillerBuild, Long> {
    @EJB
    private KillerBuildRepository repository;

    @EJB
    private UserKillerBuildRatingRepository ratingRepository;

    @EJB
    private GlobalState globalState;

    @EJB
    private Utility utility;

    @Override
    protected KillerBuildRepository getRepository() {
        return repository;
    }

    public KillerBuild generateRandomKillerBuild() {
        return utility.generateRandomKillerBuild(globalState.getKillerPerks());
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
    public UserKillerBuildRating getRatedBuild(User user, KillerBuild build) {
        return ratingRepository.findByUserAndBuild(user, build);
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

    // ------

    // 1. Случайный билд из лучших по rating (топ-10)
    public KillerBuild findRandomTopRatedBuild() {
        Optional<KillerBuild> build = repository.findRandomTopRatedBuild();
        return build.orElseThrow(() -> new RuntimeException("No top-rated builds found"));
    }

    // 2. Случайный билд из самых популярных по usageCount (топ-10)
    public KillerBuild findRandomMostPopularBuild() {
        Optional<KillerBuild> build = repository.findRandomMostPopularBuild();
        return build.orElseThrow(() -> new RuntimeException("No popular builds found"));
    }

    // 3. Случайный билд с approvedByAdmin == true
    public KillerBuild findRandomApprovedBuild() {
        Optional<KillerBuild> build = repository.findRandomApprovedBuild();
        return build.orElseThrow(() -> new RuntimeException("No approved builds found"));
    }
}
