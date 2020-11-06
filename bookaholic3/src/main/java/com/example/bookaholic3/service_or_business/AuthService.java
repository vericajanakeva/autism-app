package com.example.bookaholic3.service_or_business;

import com.example.bookaholic3.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword);
}
