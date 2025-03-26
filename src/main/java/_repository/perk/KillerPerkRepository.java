package _repository.perk;

import _repository.BaseRepository;
import jakarta.ejb.Stateless;
import model.perk.KillerPerk;

@Stateless
public class KillerPerkRepository extends BaseRepository<KillerPerk, Long> {
    public KillerPerkRepository() {
        super(KillerPerk.class);
    }
}
