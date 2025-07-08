package com.yourcompany.apigateway.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Временная заглушка - в реальном проекте здесь будет обращение к базе данных
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("$2a$10$DowJonesJy3GJiKjvlOsOhOYQhJGgLzJqwHBGZkQJ7o3vJ8JhSXDm") // "admin" закодировано в BCrypt
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
