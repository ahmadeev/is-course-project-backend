package _repository.build;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.build.KillerBuild;

@Stateless
public class KillerBuildRepository extends BaseRepository<KillerBuild, Long> {
    public KillerBuildRepository() {
        super(KillerBuild.class);
    }
}
