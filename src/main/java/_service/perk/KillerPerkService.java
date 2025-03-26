package _service.perk;

import _repository.perk.KillerPerkRepository;
import _service.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model.perk.KillerPerk;
import utils.Utility;

@Stateless
public class KillerPerkService extends BaseService<KillerPerk, Long> {
    @EJB
    private KillerPerkRepository repository;

    @EJB
    private Utility utility;

    @Override
    protected KillerPerkRepository getRepository() {
        return repository;
    }
}
