package com.example.bookaholic3.service_or_business;

import com.example.bookaholic3.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(String userId);
    User registerUser(User user);
}
