package com.scruman.backend.service;

import com.scruman.backend.entity.User;
import com.scruman.backend.exception.UserNotFoundException;
import com.scruman.backend.repository.UserRepository;
import com.scruman.utils.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest extends BaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Spy
    private User user;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder, authenticationManager);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void shouldNotFindUserByUsername() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findByUsername(username);
        });
    }

    @Test
    public void shouldSaveUser() {
        User result = userService.save(user);

        assertThat(result).isEqualTo(user);
        assertThat(result.getPassword()).isEqualTo("encoded-password");
    }

    @Test
    void shouldExistsByEmail() {
        String email = "email@mail.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.existsByEmail(email);

        assertTrue(result);
    }

    @Test
    public void shouldUpdateUser() {
        user.setId(10L);

        User result = userService.update(user);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldNotExistsByEmail() {
        String email = "email@mail.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = userService.existsByEmail(email);

        assertFalse(result);
    }

    @Test
    public void shouldFindUserByUsername() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.findByUsername(username);

        assertThat(result).isEqualTo(user);
    }
}
