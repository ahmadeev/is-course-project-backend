package auth;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import model._utils.Role;
import model._utils.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named(value = "authService")
@ApplicationScoped
public class AuthService {
    private static final String SALT = "salt";

    @PersistenceContext
    protected EntityManager em;

    @PostConstruct
    private void init() {
        System.out.println("AuthService initialized");
    }

    @Transactional
    public String createUser(User user) {
        // TODO: отсутствует проверочка
/*        User mUser = getUserByName(user.getUsername());
        AdminRequest mAdminRequest = getAdminRequestByName(user.getUsername());

        if (mUser != null || mAdminRequest != null) {
            System.out.println("meow1: " + mUser == null ? "null" : mUser.toString());
            System.out.println("meow2: " + mAdminRequest == null ? "null" : mAdminRequest.toString());
            return "User already exists";
        }*/

        // если нет роли админа, то создаем пользователя
        if (!user.getRoles().contains(Role.ROLE_ADMIN)) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            em.persist(user);
            return "User successfully signed up";
        }

        // если роль админа есть и админы в системе существуют, создаем заявку
        if (this.getAdminsList() != null && !this.getAdminsList().isEmpty()) {
            em.persist(new AdminRequest(
                    user.getUsername(),
                    BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())
            ));
            return "Successfully requested admin rights";
        }

        // если роль админа есть и админов в системе нет, создаем админа
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        em.persist(user);
        return "Admin successfully signed up";
    }

    @Transactional
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Transactional
    public User getUserByName(String name) {
        String jpql = "SELECT o FROM User o WHERE o.username = :name";
        Query query = em.createQuery(jpql, User.class);
        query.setParameter("name", name);
        List<User> users = query.getResultList();
        System.out.println(users.toString());
        if (users.isEmpty()) return null;
        return users.get(0);
    }

    @Transactional
    public AdminRequest getAdminRequestByName(String name) {
        String jpql = "SELECT o FROM AdminRequest o WHERE o.name = :name";
        Query query = em.createQuery(jpql, AdminRequest.class);
        query.setParameter("name", name);
        List<AdminRequest> users = query.getResultList();
        System.out.println(users.toString());
        if (users.isEmpty()) return null;
        return users.get(0);
    }

    @Transactional
    public List<User> getAdminsList() {
        return em.createQuery(
                        "SELECT u FROM User u WHERE :role MEMBER OF u.roles",
                        User.class
                )
                .setParameter("role", Role.ROLE_ADMIN)
                .getResultList();
    }

    public User createEntityFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(
                new HashSet<Role>(Arrays.asList(userDTO.getIsAdmin().equals("true") ?
                    new Role[]{Role.ROLE_ADMIN, Role.ROLE_USER} :
                    new Role[]{Role.ROLE_USER})));
        return user;
    }
}
