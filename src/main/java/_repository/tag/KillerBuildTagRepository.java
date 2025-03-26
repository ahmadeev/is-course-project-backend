package _repository.tag;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.match.KillerMatch;
import model.tag.KillerBuildTag;

@Stateless
public class KillerBuildTagRepository extends BaseRepository<KillerBuildTag, Long> {
    public KillerBuildTagRepository() {
        super(KillerBuildTag.class);
    }
}
