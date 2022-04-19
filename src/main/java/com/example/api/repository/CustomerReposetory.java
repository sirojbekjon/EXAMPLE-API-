package com.example.api.repository;

import com.example.api.entity.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReposetory extends JpaRepository<Customer,Integer> {
boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
    boolean existsByPhoneNumber(String phoneNumber);
}
