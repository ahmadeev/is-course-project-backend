package _service;

import _repository.KillerBuildRepository;
import _repository.KillerPerkRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import model.KillerBuild;
import model.KillerPerk;
import utils.Utility;

import java.util.List;

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
