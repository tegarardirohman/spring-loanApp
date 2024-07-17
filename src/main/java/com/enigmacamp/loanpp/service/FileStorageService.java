package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.entity.ProfilePicture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public String storeFile(MultipartFile file, String id);

    public ResponseEntity<byte[]> getImage(String id);

}
