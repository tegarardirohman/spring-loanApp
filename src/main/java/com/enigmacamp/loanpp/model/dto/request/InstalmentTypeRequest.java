package com.enigmacamp.loanpp.model.dto.request;

import com.enigmacamp.loanpp.model.entity.InstalmentType.EInstalmentType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstalmentTypeRequest {
    private String id;

    @NotBlank
    private String instalmentType;
}
