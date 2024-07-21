package com.enigmacamp.loanpp.repository;

import com.enigmacamp.loanpp.model.entity.GuaranteePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuaranteePictureRepository extends JpaRepository<GuaranteePicture, String> {
}
