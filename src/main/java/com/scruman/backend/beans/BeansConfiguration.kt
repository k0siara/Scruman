package com.scruman.backend.beans

import com.scruman.backend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class BeansConfiguration {

    @Bean
    open fun getCurrentUser(userRepository: UserRepository): CurrentUser {
        return CurrentUserImpl(userRepository)
    }

}