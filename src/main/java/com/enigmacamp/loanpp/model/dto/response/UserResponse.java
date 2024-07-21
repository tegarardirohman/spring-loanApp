package com.enigmacamp.loanpp.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private String email;
    private List<String> role;

}
