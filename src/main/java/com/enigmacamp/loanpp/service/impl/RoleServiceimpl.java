package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.entity.Role;
import com.enigmacamp.loanpp.repository.RoleRepository;
import com.enigmacamp.loanpp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceimpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role.ERole role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role.toString());

        // role available then return it
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        // if not exist, create new one
        Role currentRole = Role.builder()
                .role(role.toString())
                .build();

        return roleRepository.saveAndFlush(currentRole);
    }

}
