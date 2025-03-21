package _repository;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.Query;
import model.Dlc;
import model.KillerBuild;
import model.SurvivorBuild;
import model.utils.User;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserRepository extends BaseRepository<User, Long> {
    public UserRepository() {
        super(User.class);
    }

    // решение мне не нравится, но это одно из самых понятных
    @TransactionAttribute
    public List<KillerBuild> getFavoriteKillerBuilds(Long userId) {
        try {
            List<KillerBuild> builds = getEm()
                    .createQuery("SELECT b FROM User u JOIN u.favoriteKillerBuilds b JOIN FETCH b.perks p JOIN FETCH p.killer WHERE u.id = :userId", KillerBuild.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return builds != null ? builds : new ArrayList<>();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error fetching favorite killer builds for user with ID " + userId, e);
        }
    }

    @TransactionAttribute
    public List<SurvivorBuild> getFavoriteSurvivorBuilds(Long userId) {
        try {
            List<SurvivorBuild> builds = getEm()
                    .createQuery("SELECT b FROM User u JOIN u.favoriteSurvivorBuilds b JOIN FETCH b.perks p JOIN FETCH p.survivor WHERE u.id = :userId", SurvivorBuild.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return builds != null ? builds : new ArrayList<>();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error fetching favorite survivor builds for user with ID " + userId, e);
        }
    }
}
