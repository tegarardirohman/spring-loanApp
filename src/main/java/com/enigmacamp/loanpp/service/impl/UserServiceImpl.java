package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.dto.response.UserResponse;
import com.enigmacamp.loanpp.model.entity.AppUser;
import com.enigmacamp.loanpp.model.entity.Role;
import com.enigmacamp.loanpp.model.entity.User;
import com.enigmacamp.loanpp.repository.UserRepository;
import com.enigmacamp.loanpp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public UserResponse findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return UserResponse.builder()
                .email(user.getEmail())
                .role(user.getRoles().stream().map(Role::getRole).toList())
                .build();
    }

    @Override
    public AppUser loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
