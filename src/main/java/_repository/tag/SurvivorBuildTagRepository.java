package _repository.tag;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.tag.KillerBuildTag;
import model.tag.SurvivorBuildTag;

@Stateless
public class SurvivorBuildTagRepository extends BaseRepository<SurvivorBuildTag, Long> {
    public SurvivorBuildTagRepository() {
        super(SurvivorBuildTag.class);
    }
}
