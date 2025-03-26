package _service.tag;

import _repository.UserRepository;
import _repository.build.KillerBuildRepository;
import _repository.match.KillerMatchRepository;
import _repository.tag.KillerBuildTagRepository;
import _service.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import model._utils.User;
import model.build.KillerBuild;
import model.match.KillerMatch;
import model.tag.KillerBuildTag;
import model.tag.SurvivorBuildTag;

import java.util.List;
import java.util.Set;

@Stateless
public class KillerBuildTagService extends BaseService<KillerBuildTag, Long> {
    @EJB
    private KillerBuildTagRepository repository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private KillerBuildRepository buildRepository;

    @Override
    protected KillerBuildTagRepository getRepository() {
        return repository;
    }

    public List<KillerBuildTag> getKillerBuildTags(User user) {
        User managedUser = userRepository.findById(user.getId());
        if (managedUser == null) {
            return null;
        }
        return managedUser.getKillerBuildTags();
    }

    public List<KillerBuildTag> getKillerBuildTags(KillerBuild build) {
        KillerBuild managedBuild = buildRepository.findById(build.getId());
        if (managedBuild == null) {
            return null;
        }
        return managedBuild.getTags();
    }

    public KillerBuildTag addKillerBuildTag(KillerBuildTag tag) {
        // получаем managed
        User user = userRepository.findById(tag.getUser().getId());
        if (user == null) {
            return null;
        }
        // получаем managed
        KillerBuild build = buildRepository.findById(tag.getBuild().getId());
        if (build == null) {
            return null;
        }
        // обновляем теги у юзера и самого юзера
        user.addKillerBuildTag(tag);
        build.addKillerBuildTag(tag);
        // обновляем тег
        tag.setUser(user);
        tag.setBuild(build);
        // обновляем в бд
        return repository.create(tag);
    }

    public void removeKillerBuildTag(KillerBuildTag tag) {
        KillerBuildTag managedTag = repository.findById(tag.getId());
        if (managedTag == null) {
            throw new RuntimeException("KillerBuildTag not found");
        }
        if (managedTag.getUser().getId() != tag.getUser().getId()) {
            throw new RuntimeException("KillerBuildTag user id mismatch");
        }
        tag.getUser().removeKillerBuildTag(tag);
        tag.getBuild().removeKillerBuildTag(tag);
        repository.delete(managedTag.getId());
    }
}
