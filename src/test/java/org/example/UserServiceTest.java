package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("When the list of users is filled in, then the correct list of logins is returned.")
    void getListLogins() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Test1", "Test12"), new User("Test2", "Test22")));
        assertEquals(List.of("Test1", "Test2"), userService.getAllLogins());
    }

    @Test
    @DisplayName("When the list of users is filled in, then a non-empty list of logins is returned.")
    void getNotEmptyListLogins() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Test1", "Test12"), new User("Test2", "Test22")));
        assertNotEquals(List.of(), userService.getAllLogins());
    }

    @Test
    @DisplayName("When we add a user with invalid parameters, then we check for throwing an exception.")
    void addInvalidParametersUser() {
        assertThatThrownBy(() -> userService.addUser("", null)).isInstanceOf(IllegalArgumentException.class).hasMessage("Логин и пароль должны быть заполнены!");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    @DisplayName("When we add a user who already exists then we check the exception throw.")
    void addAlreadyExistsUser() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Test1", "Test12")));
        assertThatThrownBy(() -> userService.addUser("Test1", "Test12")).isInstanceOf(UserNonUniqueException.class);
    }

    @Test
    @DisplayName("When a user is added then we check his addition.")
    void checkAddUser() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        when(userRepository.addUser(any(User.class))).thenReturn(null);
        userService.addUser("Test1", "Test12");
        verify(userRepository).addUser(any());
    }

    @Test
    @DisplayName("When a user with the specified username and password exists then the method returns true.")
    void loginUser() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Test1", "Test12")));
        assertTrue(userService.loginUser("Test1", "Test12"));
    }
}