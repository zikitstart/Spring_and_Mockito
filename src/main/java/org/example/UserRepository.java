package org.example;

import java.util.*;

public class UserRepository {
    private final List<User> userList = new ArrayList<>();

    public User addUser(User user) {
        userList.add(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public Optional<User> getUserByLogin(String login) {
        return this.userList.stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }

    public Optional<User> getUserByParameters(String login, String password) {
        return this.userList.stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(user -> user.getPassword().equals(password))
                .findAny();
    }
}
