package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        user = new User("Test1", "Test12");
    }

    @Test
    @DisplayName("Getting an empty list of users")
    void getEmptyList() {
        assertTrue(userRepository.getAllUsers().isEmpty(), "Должен возвращаться пустой список!");
    }

    @Test
    @DisplayName("Getting a list of users when the service is initially filled in")
    void getUserList() {
        assertEquals(userRepository.getAllUsers(), new ArrayList<>(), "Должны возвращаться те же самые пользователи которых добавляли!");
    }

    @Test
    @DisplayName("Search for a user by login if there is such a user")
    void searchUserByLogin() {
        userRepository.addUser(user);
        assertEquals(userRepository.getUserByLogin(user.getLogin())
                , Optional.ofNullable(user)
                , "Пользователя с таким логином не существует!");
    }

    @Test
    @DisplayName("Search for a user by login in the case when there is no such user")
    void searchUserLogin() {
        assertNotEquals(userRepository.getUserByLogin(user.getLogin())
                , Optional.ofNullable(user)
                , "Пользователь с таким логином уже существует!");
    }

    @Test
    @DisplayName("Search for a user by username and password, if there is such a user.")
    void searchUserByParameters() {
        userRepository.addUser(user);
        assertEquals(userRepository.getUserByParameters(user.getLogin(), user.getPassword())
                , Optional.ofNullable(user)
                , "Пользователя с такими параметрами не существует!");
    }

    @Test
    @DisplayName("Search for a user by username and password in the case when the password matches one of the existing ones, but the login does not.")
    void searchUserParameters() {
        userRepository.addUser(user);
        assertNotEquals(userRepository.getUserByParameters("Test", user.getPassword())
                , Optional.ofNullable(user)
                , "Пользователя с такими параметрами существует!");
    }

    @Test
    @DisplayName("Search for a user by username and password - in the case when the username matches one of the existing ones, but the password does not.")
    void searchUserParameter() {
        userRepository.addUser(user);
        assertNotEquals(userRepository.getUserByParameters(user.getLogin(), "Test")
                , Optional.ofNullable(user)
                , "Пользователя с такими параметрами существует!");
    }
}