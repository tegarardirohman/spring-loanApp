package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.entity.AppUser;
import com.enigmacamp.loanpp.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserById(String id);
    User findById(String id);
    AppUser loadUserByEmail(String email);
}
