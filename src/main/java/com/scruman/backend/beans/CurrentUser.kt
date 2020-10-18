package com.scruman.backend.beans

import com.scruman.app.HasLogger
import com.scruman.backend.entity.User
import com.scruman.backend.repository.UserRepository
import com.scruman.backend.security.SecurityUtils
import org.springframework.security.access.prepost.PreAuthorize

@FunctionalInterface
interface CurrentUser {
    @PreAuthorize("isAuthenticated()")
    fun get(): User?
}

class CurrentUserImpl(
        private val userRepository: UserRepository
) : CurrentUser, HasLogger {
    override fun get(): User? {
        logger.debug("Getting currently authenticated user")

        val username: String = SecurityUtils.getCurrentUserUsername()
        logger.debug("Currently logger in username: {}", username)

        return userRepository.findByUsername(username)
                .orElse(null)
    }
}
