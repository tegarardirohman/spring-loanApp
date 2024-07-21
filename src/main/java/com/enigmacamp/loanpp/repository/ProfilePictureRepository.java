package com.enigmacamp.loanpp.repository;

import com.enigmacamp.loanpp.model.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
    ProfilePicture findByName(String name);
}
