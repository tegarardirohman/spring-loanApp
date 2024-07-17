package com.enigmacamp.loanpp.model.dto.response;
import com.enigmacamp.loanpp.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthResponse {
    private String email;
    private List<Role> role;
    private String token = "";
}
