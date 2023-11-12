package com.koombea.scraper.services;

import com.koombea.scraper.dto.UserDto;
import com.koombea.scraper.entity.User;
import com.koombea.scraper.exception.UsernameAlreadyExistsException;
import com.koombea.scraper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto save(UserDto user) {

        User existingUser = userRepository.findByUsername(user.getUsername());

        if(existingUser != null) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User savedUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
       userRepository.save(savedUser);

        return user;

    }

}
