package _repository.perk;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.perk.SurvivorPerk;

@Stateless
public class SurvivorPerkRepository extends BaseRepository<SurvivorPerk, Long> {
    public SurvivorPerkRepository() {
        super(SurvivorPerk.class);
    }
}
