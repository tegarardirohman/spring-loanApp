package com.enigmacamp.loanpp.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvatarResponse {
    private String name;
    private String url;
}

