package com.java14.repository;

import com.java14.entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ManagerRepository extends MongoRepository<Manager, String> {
    Manager findByAuthId(Long authId);


    Manager findByEmail(String mail);


    List<Manager> findAllByRegistrationEndDateBetween(LocalDate today, LocalDate futureDate);



}
