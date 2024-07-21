package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.InstalmentTypeRespone;
import com.enigmacamp.loanpp.model.entity.InstalmentType;
import com.enigmacamp.loanpp.repository.InstalmentTypeRepository;
import com.enigmacamp.loanpp.service.InstalmentTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstalmentTypeImpl implements InstalmentTypeService {
    private final InstalmentTypeRepository instalmentTypeRepository;

    @Override
    @Transactional
    public InstalmentTypeRespone createInstalmentType(InstalmentTypeRequest request) {

        InstalmentType instalmentType;

        try {
            instalmentType = InstalmentType.builder()
                    .instalmentType(InstalmentType.EInstalmentType.valueOf(request.getInstalmentType()))
                    .build();

            instalmentTypeRepository.saveAndFlush(instalmentType);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid instalment type");
        }

        return InstalmentTypeRespone.builder()
                .id(instalmentType.getId())
                .instalmentType(instalmentType.getInstalmentType().toString())
                .build();
    }

    @Override
    @Transactional
    public InstalmentTypeRespone getInstalmentTypeById(String id) {
        return convertInstalmentTypeToRespone(instalmentTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalment type not found")));
    }


    @Transactional
    @Override
    public List<InstalmentTypeRespone> getAllInstalment() {
        List<InstalmentType> instalmentTypes = instalmentTypeRepository.findAll();

        return instalmentTypes.stream()
                .map(this::convertInstalmentTypeToRespone)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InstalmentTypeRespone updateInstalmentType(InstalmentTypeRequest request) {
        InstalmentType instalmentType = instalmentTypeRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalment type not found"));
        instalmentType.setInstalmentType(InstalmentType.EInstalmentType.valueOf(request.getInstalmentType()));

        return convertInstalmentTypeToRespone(instalmentTypeRepository.saveAndFlush(instalmentType));
    }

    @Override
    @Transactional
    public void deleteInstalmentType(String id) {
        InstalmentType instalmentType = instalmentTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalment type not found"));
        instalmentTypeRepository.delete(instalmentType);
    }

    private InstalmentTypeRespone convertInstalmentTypeToRespone(InstalmentType instalmentType) {

        return InstalmentTypeRespone.builder()
                .id(instalmentType.getId())
                .instalmentType(String.valueOf(instalmentType.getInstalmentType()))
                .build();
    }
}
