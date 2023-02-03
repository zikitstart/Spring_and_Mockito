package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllLogins() {
        return this.userRepository.getAllUsers().stream().map(User::getLogin).collect(Collectors.toList());
    }

    public void addUser(String login, String password) {
        User user = new User(login, password);
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Логин и пароль должны быть заполнены!");
        }
        boolean userExist = this.userRepository.getAllUsers().stream().anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new UserNonUniqueException();
        }
        this.userRepository.addUser(user);
    }

    public boolean loginUser(String login, String password) {
        User user = new User(login, password);
        return this.userRepository.getAllUsers().stream().anyMatch(u -> u.equals(user));
    }
}
