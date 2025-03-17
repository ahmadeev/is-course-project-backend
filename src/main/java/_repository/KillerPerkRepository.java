package _repository;

import jakarta.ejb.Stateless;
import model.Dlc;
import model.KillerPerk;

@Stateless
public class KillerPerkRepository extends BaseRepository<KillerPerk, Long> {
    public KillerPerkRepository() {
        super(KillerPerk.class);
    }
}
