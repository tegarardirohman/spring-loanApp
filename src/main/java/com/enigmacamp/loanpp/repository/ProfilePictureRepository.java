package com.enigmacamp.loanpp.repository;

import com.enigmacamp.loanpp.model.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
}
