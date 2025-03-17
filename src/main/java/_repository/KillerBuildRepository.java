package _repository;

import jakarta.ejb.Stateless;
import model.KillerBuild;

@Stateless
public class KillerBuildRepository extends BaseRepository<KillerBuild, Long> {
    public KillerBuildRepository() {
        super(KillerBuild.class);
    }
}
