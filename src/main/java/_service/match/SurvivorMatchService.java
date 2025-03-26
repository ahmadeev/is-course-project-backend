package _service.match;

import _repository.UserRepository;
import _repository.build.SurvivorBuildRepository;
import _repository.match.SurvivorMatchRepository;
import _service.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model._utils.User;
import model.build.SurvivorBuild;
import model.match.SurvivorMatch;

import java.util.List;

@Stateless
public class SurvivorMatchService extends BaseService<SurvivorMatch, Long> {
    @EJB
    private SurvivorMatchRepository repository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private SurvivorBuildRepository buildRepository;

    @Override
    protected SurvivorMatchRepository getRepository() {
        return repository;
    }

    public SurvivorMatch addSurvivorMatch(SurvivorMatch match) {
        // получаем managed
        User user = userRepository.findById(match.getUser().getId());
        if (user == null) {
            return null;
        }
        // получаем managed
        SurvivorBuild build = buildRepository.create(match.getBuild());
        // обновляем match
        match.setUser(user);
        match.setBuild(build);
        // обновляем матчи у юзера и самого юзера
        user.addSurvivorMatch(match);
        // TODO: отдельно обновить билд + вспомнить про методы
        userRepository.update(user);
        return match;
    }

    public List<SurvivorMatch> getSurvivorMatches(User user) {
        User managedUser = userRepository.findById(user.getId());
        if (managedUser == null) {
            return null;
        }
        return managedUser.getSurvivorMatches();
    }
}
