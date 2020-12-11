package com.scruman.backend.service;

import com.scruman.backend.entity.User;
import com.scruman.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTestIT {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Test
    public void shouldSaveUser() {
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
        Long savedUserId = userService.save(new User(
                "Adam", "Testowy", "atestowy", "a.testowy@mail.com", "password",
                true, true, true, true, new HashSet<>(), new HashSet<>(), new HashSet<>(),
                null
        )).getId();

        Optional<User> optionalUser = userRepository.findById(savedUserId);

        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertThat(user.getFirstName()).isEqualTo("Adam");
        assertThat(user.getLastName()).isEqualTo("Testowy");
        assertThat(user.getUsername()).isEqualTo("atestowy");
        assertThat(user.getEmail()).isEqualTo("a.testowy@mail.com");
        assertThat(user.getPassword()).isEqualTo("encoded-password");
        assertThat(user.isAccountExpired()).isEqualTo(false);
        assertThat(user.isAccountLocked()).isEqualTo(false);
        assertThat(user.isCredentialsExpired()).isEqualTo(false);
        assertThat(user.isEnabled()).isEqualTo(true);
    }
}
