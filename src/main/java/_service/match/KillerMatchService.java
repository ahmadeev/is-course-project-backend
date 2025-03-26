package _service.match;

import _repository.UserRepository;
import _repository.build.KillerBuildRepository;
import _repository.match.KillerMatchRepository;
import _service.BaseService;
import dto.build.KillerBuildDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model._utils.User;
import model.build.KillerBuild;
import model.match.KillerMatch;

import java.util.Arrays;
import java.util.List;

@Stateless
public class KillerMatchService extends BaseService<KillerMatch, Long> {
    @EJB
    private KillerMatchRepository repository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private KillerBuildRepository buildRepository;

    @Override
    protected KillerMatchRepository getRepository() {
        return repository;
    }

    public KillerMatch addKillerMatch(KillerMatch match) {
        // получаем managed
        User user = userRepository.findById(match.getUser().getId());
        if (user == null) {
            return null;
        }
        // получаем managed
        KillerBuild build = buildRepository.create(match.getBuild());
        // обновляем match
        match.setUser(user);
        match.setBuild(build);
        // обновляем матчи у юзера и самого юзера
        user.addKillerMatch(match);
        userRepository.update(user);
        return match;
    }

    public List<KillerMatch> getKillerMatches(User user) {
        User managedUser = userRepository.findById(user.getId());
        if (managedUser == null) {
            return null;
        }
        return managedUser.getKillerMatches();
    }
}
