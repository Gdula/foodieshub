package com.gdula.foodieshub.config;

import com.gdula.foodieshub.model.User;
import com.gdula.foodieshub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByLogin(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User does not exists!");
        }

        String role = user.getLogin().equals("admin") ? "ADMIN" : "USER";
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .roles(role)
                .password( user.getPassword())
                .build();
    }
}
