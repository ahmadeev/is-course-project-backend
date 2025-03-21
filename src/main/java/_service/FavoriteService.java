package _service;

import _repository.KillerBuildRepository;
import _repository.SurvivorBuildRepository;
import _repository.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model.KillerBuild;
import model.SurvivorBuild;
import model.utils.User;

import java.util.List;

@Stateless
public class FavoriteService {
    @EJB
    protected UserRepository userRepository;

    @EJB
    protected SurvivorBuildRepository survivorBuildRepository;

    @EJB
    protected KillerBuildRepository killerBuildRepository;

    public List<SurvivorBuild> getFavoriteSurvivorBuilds(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getFavoriteSurvivorBuilds();
    }

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

    public List<KillerBuild> getFavoriteKillerBuilds(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getFavoriteKillerBuilds();
    }

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
