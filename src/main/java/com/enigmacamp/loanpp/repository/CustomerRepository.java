package com.enigmacamp.loanpp.repository;

import com.enigmacamp.loanpp.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
