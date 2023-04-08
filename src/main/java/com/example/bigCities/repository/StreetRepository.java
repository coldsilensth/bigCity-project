package com.example.bigCities.repository;

import com.example.bigCities.entity.City;
import com.example.bigCities.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StreetRepository extends JpaRepository<Street, Long> {
    Street findByName(String name);
}
