package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.InstalmentTypeRespone;

import java.util.List;

public interface InstalmentTypeService {
    InstalmentTypeRespone createInstalmentType(InstalmentTypeRequest instalmentTypeRequest);
    InstalmentTypeRespone getInstalmentTypeById(String id);
    List<InstalmentTypeRespone> getAllInstalment();
    InstalmentTypeRespone updateInstalmentType(InstalmentTypeRequest instalmentTypeRequest);
    void deleteInstalmentType(String id);
}
