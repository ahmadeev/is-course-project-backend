package _repository.match;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.match.KillerMatch;

@Stateless
public class KillerMatchRepository extends BaseRepository<KillerMatch, Long> {
    public KillerMatchRepository() {
        super(KillerMatch.class);
    }
}
