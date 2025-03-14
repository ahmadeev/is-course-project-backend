package _repository;

import jakarta.ejb.Stateless;
import model.Dlc;

@Stateless
public class DlcRepository extends BaseRepository<Dlc, Long> {
    public DlcRepository() {
        super(Dlc.class);
    }
}
