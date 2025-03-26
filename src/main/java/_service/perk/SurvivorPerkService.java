package _service.perk;

import _repository.perk.SurvivorPerkRepository;
import _service.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model.perk.SurvivorPerk;
import utils.Utility;

@Stateless
public class SurvivorPerkService extends BaseService<SurvivorPerk, Long> {
    @EJB
    private SurvivorPerkRepository repository;

    @EJB
    private Utility utility;

    @Override
    protected SurvivorPerkRepository getRepository() {
        return repository;
    }
}
