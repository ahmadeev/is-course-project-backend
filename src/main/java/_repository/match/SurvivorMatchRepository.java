package _repository.match;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.match.SurvivorMatch;

@Stateless
public class SurvivorMatchRepository extends BaseRepository<SurvivorMatch, Long> {
    public SurvivorMatchRepository() {
        super(SurvivorMatch.class);
    }
}
