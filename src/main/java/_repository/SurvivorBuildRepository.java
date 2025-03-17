package _repository;

import jakarta.ejb.Stateless;
import model.SurvivorBuild;

@Stateless
public class SurvivorBuildRepository extends BaseRepository<SurvivorBuild, Long> {
    public SurvivorBuildRepository() {
        super(SurvivorBuild.class);
    }
}
