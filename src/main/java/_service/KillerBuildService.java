package _service;

import _repository.KillerBuildRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.KillerBuild;
import model.KillerPerk;
import model.SurvivorBuild;
import utils.Utility;

import java.util.List;

@Stateless
public class KillerBuildService extends BaseService<KillerBuild, Long> {
    @EJB
    private KillerBuildRepository repository;

    @EJB
    private Utility utility;

    @Override
    protected KillerBuildRepository getRepository() {
        return repository;
    }

    public KillerBuild generateRandomKillerBuild(List<KillerPerk> perks) {
        return utility.generateRandomKillerBuild(perks);
    }

    @TransactionAttribute
    public KillerBuild approveKillerBuild(KillerBuild build) {
        return repository.update(build);
    }
}
