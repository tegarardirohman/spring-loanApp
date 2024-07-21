package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.dto.request.AuthRequest;
import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.AuthResponse;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.entity.AppUser;
import com.enigmacamp.loanpp.model.entity.Customer;
import com.enigmacamp.loanpp.model.entity.Role;
import com.enigmacamp.loanpp.model.entity.User;
import com.enigmacamp.loanpp.repository.CustomerRepository;
import com.enigmacamp.loanpp.repository.RoleRepository;
import com.enigmacamp.loanpp.repository.UserRepository;
import com.enigmacamp.loanpp.security.JwtUtil;
import com.enigmacamp.loanpp.service.AuthService;
import com.enigmacamp.loanpp.service.RoleService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Validator validator;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional
    public AuthResponse register(AuthRequest request, Role.ERole new_role) {
        Set<ConstraintViolation<AuthRequest>> constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        Role role = roleService.getOrSave(new_role);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.saveAndFlush(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRoles().stream().map(Role::getRole).toList())
                .build();
    }

    @Override
    @Transactional
    public AuthResponse register(CustomerRequest request, Role.ERole new_role) {
        Set<ConstraintViolation<CustomerRequest>> constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        if (userRepository.findByEmail(request.getUser().getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        Role role = roleService.getOrSave(new_role);
        List<Role> roles = new ArrayList<>();
        roles.add(role);


        // user
        User user = request.getUser();
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.saveAndFlush(user);

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .phone(request.getPhone())
                .status(request.getStatus())
                .user(user)
                .build();

        // save customer
        customerRepository.saveAndFlush(customer);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRoles().stream().map(Role::getRole).toList())
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(appUser);

        return AuthResponse.builder()
                .email(appUser.getEmail())
                .role(appUser.getRoles().stream().map(Role::getRole).toList())
                .token(token)
                .build();
    }

}
