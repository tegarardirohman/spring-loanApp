package com.enigmacamp.loanpp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class InstalmentTypeRespone {
    private String id;
    private String instalmentType;
}
