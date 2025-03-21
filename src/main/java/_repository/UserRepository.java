package _repository;

import jakarta.ejb.Stateless;
import model.Dlc;
import model.utils.User;

@Stateless
public class UserRepository extends BaseRepository<User, Long> {
    public UserRepository() {
        super(User.class);
    }
}
