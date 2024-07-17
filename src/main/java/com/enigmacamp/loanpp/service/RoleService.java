package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.entity.Role;

public interface RoleService {
    Role getOrSave(Role.ERole role);
}
