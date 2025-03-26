package _repository.build;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.build.SurvivorBuild;

@Stateless
public class SurvivorBuildRepository extends BaseRepository<SurvivorBuild, Long> {
    public SurvivorBuildRepository() {
        super(SurvivorBuild.class);
    }
}
