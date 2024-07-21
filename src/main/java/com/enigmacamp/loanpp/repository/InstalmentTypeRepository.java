package com.enigmacamp.loanpp.repository;

import com.enigmacamp.loanpp.model.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, String> {
}
