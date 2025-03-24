package _repository.utils;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.KillerBuild;
import model.utils.User;
import model.utils.UserKillerBuildRating;

import java.util.List;

@Stateless
public class UserKillerBuildRatingRepository extends BaseRepository<UserKillerBuildRating, Long> {
    public UserKillerBuildRatingRepository() {
        super(UserKillerBuildRating.class);
    }

    public List<UserKillerBuildRating> findByUser(User user) {
        String jpql = "SELECT ur FROM UserKillerBuildRating ur WHERE ur.user = :user";
        TypedQuery<UserKillerBuildRating> query = getEm().createQuery(jpql, UserKillerBuildRating.class);
        query.setParameter("user", user);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public UserKillerBuildRating findByUserAndBuild(User user, KillerBuild build) {
        String jpql = "SELECT ur FROM UserKillerBuildRating ur WHERE ur.user = :user AND ur.build = :build";
        TypedQuery<UserKillerBuildRating> query = getEm().createQuery(jpql, UserKillerBuildRating.class);
        query.setParameter("user", user);
        query.setParameter("build", build);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
