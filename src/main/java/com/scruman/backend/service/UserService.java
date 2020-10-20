package com.scruman.backend.service;

import com.scruman.backend.entity.AbstractEntity;
import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.User;
import com.scruman.backend.entity.UserProject;
import com.scruman.backend.exception.UserNotFoundException;
import com.scruman.backend.repository.UserRepository;
import com.scruman.backend.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        user.setCredentialsExpired(false);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByEmail(username);
    }

    public List<Project> getCurrentUserProjects() {
        User currentUser = getCurrentUser();
        return currentUser.getUserProjects().stream()
                .map(UserProject::getProject)
                .sorted(Comparator.comparing(AbstractEntity::getId))
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        String username = SecurityUtils.getCurrentUserUsername();
        return findByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found by username: " + username));
    }

    public User saveLastOpenedProject(User user, Project project) {
        user.setLastOpenedProject(project);
        return userRepository.save(user);
    }

    public boolean isPasswordCorrect(String value, String password) {
        return passwordEncoder.matches(value, password);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}