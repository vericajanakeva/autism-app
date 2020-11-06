package com.example.bookaholic3.service_or_business.impl;

import com.example.bookaholic3.model.Role;
import com.example.bookaholic3.model.User;
import com.example.bookaholic3.model.exception.PasswordDoesntMatchException;
import com.example.bookaholic3.persistence_or_repository.RoleRepository;
import com.example.bookaholic3.persistence_or_repository.UserRepository;
import com.example.bookaholic3.service_or_business.AuthService;
import com.example.bookaholic3.service_or_business.UserService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bookaholic3.service_or_business.utils.CreateUsers;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public User getCurrentUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username,
                           String password,
                           String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            throw new PasswordDoesntMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);
    }


    @PostConstruct
    public void init() {
        if (!this.userRepository.existsById("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.passwordEncoder.encode("admin"));
            Role role=new Role();
            //role.setId(1L);
            role.setName("ROLE_ADMIN");
            this.roleRepository.save(role);
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }
        if(userRepository.findAll().size() == 0) {
            CreateUsers createUsers = new CreateUsers();
            createUsers.init();
        }
    }
}
