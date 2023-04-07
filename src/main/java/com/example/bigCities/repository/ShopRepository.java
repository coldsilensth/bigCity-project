package com.example.bigCities.repository;

import com.example.bigCities.entity.City;
import com.example.bigCities.entity.Shop;
import com.example.bigCities.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Page<Shop> findAllByCityAndStreet(City city,Street street, Pageable pageable);
    Page<Shop> findAllByCity(City city, Pageable pageable);
    Page<Shop> findAllByStreet(Street street, Pageable pageable);
    List<Shop> findAll();
}
