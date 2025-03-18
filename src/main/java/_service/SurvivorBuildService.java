package _service;

import _repository.SurvivorBuildRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.*;
import utils.Utility;

import java.util.List;

@Stateless
public class SurvivorBuildService extends BaseService<SurvivorBuild, Long> {
    @EJB
    private SurvivorBuildRepository repository;

    @EJB
    private Utility utility;

    @Override
    protected SurvivorBuildRepository getRepository() {
        return repository;
    }

    public SurvivorBuild generateRandomSurvivorBuild(List<SurvivorPerk> perks) {
        return utility.generateRandomSurvivorBuild(perks);
    }

    @TransactionAttribute
    public SurvivorBuild approveSurvivorBuild(SurvivorBuild build) {
        return repository.update(build);
    }
}
