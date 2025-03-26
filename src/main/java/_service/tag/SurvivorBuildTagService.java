package _service.tag;

import _repository.UserRepository;
import _repository.build.SurvivorBuildRepository;
import _repository.build.SurvivorBuildRepository;
import _repository.tag.SurvivorBuildTagRepository;
import _repository.tag.SurvivorBuildTagRepository;
import _service.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model._utils.User;
import model.build.SurvivorBuild;
import model.build.SurvivorBuild;
import model.match.SurvivorMatch;
import model.tag.SurvivorBuildTag;
import model.tag.SurvivorBuildTag;
import model.tag.SurvivorBuildTag;

import java.util.List;
import java.util.Set;

@Stateless
public class SurvivorBuildTagService extends BaseService<SurvivorBuildTag, Long> {
    @EJB
    private SurvivorBuildTagRepository repository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private SurvivorBuildRepository buildRepository;

    @Override
    protected SurvivorBuildTagRepository getRepository() {
        return repository;
    }

    public List<SurvivorBuildTag> getSurvivorBuildTags(User user) {
        User managedUser = userRepository.findById(user.getId());
        if (managedUser == null) {
            return null;
        }
        return managedUser.getSurvivorBuildTags();
    }

    public List<SurvivorBuildTag> getSurvivorBuildTags(SurvivorBuild build) {
        SurvivorBuild managedBuild = buildRepository.findById(build.getId());
        if (managedBuild == null) {
            return null;
        }
        return managedBuild.getTags();
    }

    public SurvivorBuildTag addSurvivorBuildTag(SurvivorBuildTag tag) {
        // получаем managed
        User user = userRepository.findById(tag.getUser().getId());
        if (user == null) {
            return null;
        }
        // получаем managed
        SurvivorBuild build = buildRepository.findById(tag.getBuild().getId());
        if (build == null) {
            return null;
        }
        // обновляем теги у юзера и самого юзера
        user.addSurvivorBuildTag(tag);
        build.addSurvivorBuildTag(tag);
        // обновляем тег
        tag.setUser(user);
        tag.setBuild(build);
        // обновляем в бд
        return repository.create(tag);
    }

    public void removeSurvivorBuildTag(SurvivorBuildTag tag) {
        SurvivorBuildTag managedTag = repository.findById(tag.getId());
        if (managedTag == null) {
            throw new RuntimeException("SurvivorBuildTag not found");
        }
        if (managedTag.getUser().getId() != tag.getUser().getId()) {
            throw new RuntimeException("SurvivorBuildTag user id mismatch");
        }
        tag.getUser().removeSurvivorBuildTag(tag);
        tag.getBuild().removeSurvivorBuildTag(tag);
        repository.delete(managedTag.getId());
    }
}
