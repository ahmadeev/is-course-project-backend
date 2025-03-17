package _repository;

import jakarta.ejb.Stateless;
import model.KillerPerk;
import model.SurvivorPerk;

@Stateless
public class SurvivorPerkRepository extends BaseRepository<SurvivorPerk, Long> {
    public SurvivorPerkRepository() {
        super(SurvivorPerk.class);
    }
}
