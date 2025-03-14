package _service;

import _repository.DlcRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model.Dlc;

@Stateless
public class DlcService extends BaseService<Dlc, Long> {
    @EJB
    private DlcRepository repository;

    @Override
    protected DlcRepository getRepository() {
        return repository;
    }
}
