package _repository.build;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import model.build.KillerBuild;
import model.build.SurvivorBuild;

import java.util.Optional;

@Stateless
public class SurvivorBuildRepository extends BaseRepository<SurvivorBuild, Long> {
    public SurvivorBuildRepository() {
        super(SurvivorBuild.class);
    }

    // 1. Случайный билд из лучших по rating (топ-10)
    public Optional<SurvivorBuild> findRandomTopRatedBuild() {
        Query query = em.createQuery(
                "SELECT kb FROM SurvivorBuild kb " +
                        "WHERE kb.rating IN (SELECT kb2.rating FROM SurvivorBuild kb2 WHERE kb2.rating > 0 ORDER BY kb2.rating DESC LIMIT 10) " +
                        "ORDER BY RANDOM() LIMIT 1"
        );
        try {
            SurvivorBuild result = (SurvivorBuild) query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // 2. Случайный билд из самых популярных по usageCount (топ-10)
    public Optional<SurvivorBuild> findRandomMostPopularBuild() {
        Query query = em.createQuery(
                "SELECT kb FROM SurvivorBuild kb " +
                        "WHERE kb.usageCount IN (SELECT kb2.usageCount FROM SurvivorBuild kb2 WHERE kb2.usageCount > 1 ORDER BY kb2.usageCount DESC LIMIT 10) " +
                        "ORDER BY RANDOM() LIMIT 1"
        );
        try {
            SurvivorBuild result = (SurvivorBuild) query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // 3. Случайный билд с approvedByAdmin == true
    public Optional<SurvivorBuild> findRandomApprovedBuild() {
        Query query = em.createQuery(
                "SELECT kb FROM SurvivorBuild kb " +
                        "WHERE kb.approvedByAdmin = true " +
                        "ORDER BY RANDOM() LIMIT 1"
        );
        try {
            SurvivorBuild result = (SurvivorBuild) query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
