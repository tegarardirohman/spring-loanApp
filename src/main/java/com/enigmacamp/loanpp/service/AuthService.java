package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.dto.request.AuthRequest;
import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.AuthResponse;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.entity.Customer;
import com.enigmacamp.loanpp.model.entity.Role;

public interface AuthService {
    AuthResponse register(AuthRequest authRequest, Role.ERole role);
    AuthResponse register(CustomerRequest authRequest, Role.ERole role);
    AuthResponse login(AuthRequest loginRequest);
}
