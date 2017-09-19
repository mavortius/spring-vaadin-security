package com.company.app

import com.vaadin.server.CustomizedSystemMessages
import com.vaadin.server.SystemMessagesInfo
import com.vaadin.server.SystemMessagesProvider
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.vaadin.spring.security.annotation.EnableVaadinManagedSecurity
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer

@SpringBootApplication(exclude = SecurityAutoConfiguration)
@EnableVaadinManagedSecurity
class Application {

    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    /**
     * Provide custom system messages to make sure the application is reloaded when the session expires.
     */
    @Bean
    SystemMessagesProvider messagesProvider() {
        return { SystemMessagesInfo systemMessagesInfo ->
            CustomizedSystemMessages messages = new CustomizedSystemMessages()

            messages.sessionExpiredNotificationEnabled = true

            return messages
        }
    }

    /**
     * Configure the authentication manager.
     */
    @Configuration
    static class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

        @Override
        void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser('user').password('user').roles('USER')
                    .and()
                    .withUser('admin').password('admin').roles('ADMIN')
        }
    }
}
