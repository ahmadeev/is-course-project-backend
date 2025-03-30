package _repository.build;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import model.build.KillerBuild;
import model.perk.KillerPerk;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class KillerBuildRepository extends BaseRepository<KillerBuild, Long> {
    public KillerBuildRepository() {
        super(KillerBuild.class);
    }

    @Override
    public KillerBuild create(KillerBuild entity) {
        if (entity == null || entity.getPerks() == null || entity.getPerks().size() != 4) {
            throw new IllegalArgumentException("KillerBuild must have exactly 4 perks");
        }

        // сортировка (иначе ломается, такое правило)
        List<Long> newPerkIds = entity.getPerks().stream()
                .map(KillerPerk::getId)
                .sorted()
                .collect(Collectors.toList());

        List<KillerBuild> allBuilds = em.createQuery(
                "SELECT kb FROM KillerBuild kb JOIN FETCH kb.perks p",
                KillerBuild.class
        ).getResultList();

        KillerBuild existingBuild = allBuilds.stream()
                .filter(kb -> {
                    List<Long> existingPerkIds = kb.getPerks().stream()
                            .map(KillerPerk::getId)
                            .sorted()
                            .collect(Collectors.toList());
                    return existingPerkIds.equals(newPerkIds);
                })
                .findFirst()
                .orElse(null);

        if (existingBuild != null) {
            // билд существует -- увеличиваем usage_count
            existingBuild.setUsageCount(existingBuild.getUsageCount() + 1);
            em.merge(existingBuild);
            return existingBuild;
        } else {
            // билда нет -- сохраняем новый
            em.persist(entity);
            return entity;
        }
    }

    // 1. Случайный билд из лучших по rating (топ-10)
    public Optional<KillerBuild> findRandomTopRatedBuild() {
        Query query = em.createQuery(
                "SELECT kb FROM KillerBuild kb " +
                        "WHERE kb.rating IN (SELECT kb2.rating FROM KillerBuild kb2 WHERE kb2.rating > 0 ORDER BY kb2.rating DESC LIMIT 10) " +
                        "ORDER BY RANDOM() LIMIT 1"
        );
        try {
            KillerBuild result = (KillerBuild) query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // 2. Случайный билд из самых популярных по usageCount (топ-10)
    public Optional<KillerBuild> findRandomMostPopularBuild() {
        Query query = em.createQuery(
                "SELECT kb FROM KillerBuild kb " +
                        "WHERE kb.usageCount IN (SELECT kb2.usageCount FROM KillerBuild kb2 WHERE kb2.usageCount > 1 ORDER BY kb2.usageCount DESC LIMIT 10) " +
                        "ORDER BY RANDOM() LIMIT 1"
        );
        try {
            KillerBuild result = (KillerBuild) query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // 3. Случайный билд с approvedByAdmin == true
    public Optional<KillerBuild> findRandomApprovedBuild() {
        Query query = em.createQuery(
                "SELECT kb FROM KillerBuild kb " +
                        "WHERE kb.approvedByAdmin = true " +
                        "ORDER BY RANDOM() LIMIT 1"
        );
        try {
            KillerBuild result = (KillerBuild) query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
