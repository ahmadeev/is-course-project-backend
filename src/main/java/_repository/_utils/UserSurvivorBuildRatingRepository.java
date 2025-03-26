package _repository._utils;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.build.SurvivorBuild;
import model._utils.User;
import model._utils.rating.UserSurvivorBuildRating;

import java.util.List;

@Stateless
public class UserSurvivorBuildRatingRepository extends BaseRepository<UserSurvivorBuildRating, Long> {
    public UserSurvivorBuildRatingRepository() {
        super(UserSurvivorBuildRating.class);
    }

    public List<UserSurvivorBuildRating> findByUser(User user) {
        String jpql = "SELECT ur FROM UserSurvivorBuildRating ur WHERE ur.user = :user";
        TypedQuery<UserSurvivorBuildRating> query = getEm().createQuery(jpql, UserSurvivorBuildRating.class);
        query.setParameter("user", user);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public UserSurvivorBuildRating findByUserAndBuild(User user, SurvivorBuild build) {
        String jpql = "SELECT ur FROM UserSurvivorBuildRating ur WHERE ur.user = :user AND ur.build = :build";
        TypedQuery<UserSurvivorBuildRating> query = getEm().createQuery(jpql, UserSurvivorBuildRating.class);
        query.setParameter("user", user);
        query.setParameter("build", build);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
