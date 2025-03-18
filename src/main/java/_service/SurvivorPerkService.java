package _service;

import _repository.KillerPerkRepository;
import _repository.SurvivorPerkRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model.KillerPerk;
import model.SurvivorPerk;
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
