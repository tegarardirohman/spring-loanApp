package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.entity.Customer;
import com.enigmacamp.loanpp.model.entity.ProfilePicture;
import com.enigmacamp.loanpp.repository.CustomerRepository;
import com.enigmacamp.loanpp.repository.ProfilePictureRepository;
import com.enigmacamp.loanpp.service.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;
    private final ProfilePictureRepository profilePictureRepository;
    private final CustomerRepository customerRepository;

    public FileStorageServiceImpl(ProfilePictureRepository profilePictureRepository, CustomerRepository customerRepository) {
        this.fileStorageLocation = Path.of("assets/images/");
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create directory", e);
        }
        this.profilePictureRepository = profilePictureRepository;
        this.customerRepository = customerRepository;
    }

    public String getFileExtension(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        return StringUtils.getFilenameExtension(fileName);
    }

    @Override
    @Transactional
    public String storeFile(MultipartFile file, String id) {

        String idFileName = id+"."+getFileExtension(file);

        try {
            Path targetLocation = fileStorageLocation.resolve(idFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // if sucess
            // get Customer data
            Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

            ProfilePicture profilePicture = profilePictureRepository.findByName(idFileName);

            ProfilePicture profile;

            if (profilePicture == null) {
                // build profile picture
                profile = ProfilePicture.builder()
                        .url("/api/customers/" + id + "/avatar")
                        .size((int) file.getSize())
                        .contentType(file.getContentType())
                        .name(idFileName)
                        .build();
            } else {
                profile = profilePicture;
                profile.setSize((int) file.getSize());
                profile.setContentType(file.getContentType());
            }

            // save and flush
            customer.setProfilePicture(profile);
            profilePictureRepository.saveAndFlush(profile);
            customerRepository.saveAndFlush(customer);


            return idFileName;

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", e);
        }
    }

    @Override
    public ResponseEntity<byte[]> getImage(String id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        try {

            Path targetLocation = fileStorageLocation.resolve(customer.getProfilePicture().getName());

            System.out.println("TARGET: " + targetLocation);

            byte[] bytes = Files.readAllBytes(targetLocation);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/png"));
            headers.setContentDispositionFormData("attachment", customer.getProfilePicture().getName());
            headers.setContentLength(bytes.length);

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage() + " Not Found!");
        }

    }
}


