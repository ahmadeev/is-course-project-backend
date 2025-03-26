package _service;

import _repository.build.KillerBuildRepository;
import _repository.build.SurvivorBuildRepository;
import _repository.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import model._utils.User;

import java.util.List;

@Stateless
public class FavoriteService {
    @EJB
    protected UserRepository userRepository;

    @EJB
    protected SurvivorBuildRepository survivorBuildRepository;

    @EJB
    protected KillerBuildRepository killerBuildRepository;

    @TransactionAttribute
    public List<SurvivorBuild> getFavoriteSurvivorBuilds(Long userId) {
        List<SurvivorBuild> builds = userRepository.getFavoriteSurvivorBuilds(userId);
        if (builds == null) {
            throw new IllegalArgumentException("Builds not found");
        }
        return builds;
    }

    @TransactionAttribute
    public void addSurvivorBuildToFavorites(Long userId, Long buildId) {
        User user = userRepository.findById(userId);
        SurvivorBuild build = survivorBuildRepository.findById(buildId);

        if (user == null || build == null) {
            throw new IllegalArgumentException("User or SurvivorBuild not found");
        }

        if (!user.getFavoriteSurvivorBuilds().contains(build)) {
            user.addFavoriteSurvivorBuild(build);
            userRepository.update(user);
        }
    }

    @TransactionAttribute
    public void removeSurvivorBuildFromFavorites(Long userId, Long buildId) {
        User user = userRepository.findById(userId);
        SurvivorBuild build = survivorBuildRepository.findById(buildId);

        if (user == null || build == null) {
            throw new IllegalArgumentException("User or SurvivorBuild not found");
        }

        if (user.getFavoriteSurvivorBuilds().contains(build)) {
            user.removeFavoriteSurvivorBuild(build);
            userRepository.update(user);
        }
    }

    @TransactionAttribute
    public List<KillerBuild> getFavoriteKillerBuilds(Long userId) {
        List<KillerBuild> builds = userRepository.getFavoriteKillerBuilds(userId);
        if (builds == null) {
            throw new IllegalArgumentException("Builds not found");
        }
        return builds;
    }

    @TransactionAttribute
    public void addKillerBuildToFavorites(Long userId, Long buildId) {
        User user = userRepository.findById(userId);
        KillerBuild build = killerBuildRepository.findById(buildId);

        if (user == null || build == null) {
            throw new IllegalArgumentException("User or KillerBuild not found");
        }

        if (!user.getFavoriteKillerBuilds().contains(build)) {
            user.addFavoriteKillerBuild(build);
            userRepository.update(user);
        }
    }

    @TransactionAttribute
    public void removeKillerBuildFromFavorites(Long userId, Long buildId) {
        User user = userRepository.findById(userId);
        KillerBuild build = killerBuildRepository.findById(buildId);

        if (user == null || build == null) {
            throw new IllegalArgumentException("User or KillerBuild not found");
        }

        if (user.getFavoriteKillerBuilds().contains(build)) {
            user.removeFavoriteKillerBuild(build);
            userRepository.update(user);
        }
    }
}
