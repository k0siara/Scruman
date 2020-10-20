package com.scruman.backend.beans;

import com.scruman.app.HasLogger;
import com.scruman.backend.entity.User;
import com.scruman.backend.repository.UserRepository;
import com.scruman.backend.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentUserImpl implements CurrentUser, HasLogger {

    private UserRepository userRepository;

    @Override
    public User get() {
        getLogger().debug("Getting currently authenticated user");

        String username = SecurityUtils.getCurrentUserUsername();
        getLogger().debug("Currently logger in username: {}", username);

        return userRepository.findByUsername(username)
                .orElse(null);
    }
}
