package utils;

import model.utils.Role;
import model.utils.User;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class UserGenerator {
    private static final String[] NAMES = {"Alex", "John", "Emily", "Michael", "Sarah", "David", "Emma", "Daniel", "Olivia", "James"};
    private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "outlook.com", "mail.ru", "yandex.ru"};

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    private static final Random RANDOM = new SecureRandom();

    public static User generateUser() {
        String username = generateUsername();
        String email = generateEmail(username);
        String password = generatePassword(10);
        String hashedPassword = hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRoles(new HashSet<>(RANDOM.nextBoolean() ? List.of(Role.ROLE_ADMIN, Role.ROLE_USER) : List.of(Role.ROLE_USER)));

        return user;
    }

    public static String generateUsername() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        int number = RANDOM.nextInt(1000);
        return name + number;
    }

    public static String generateEmail(String username) {
        String domain = DOMAINS[RANDOM.nextInt(DOMAINS.length)];
        return username.toLowerCase() + "@" + domain;
    }

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
