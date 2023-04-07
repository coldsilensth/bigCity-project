package com.example.bigCities.service;


import com.example.bigCities.DTO.StreetDTO;
import com.example.bigCities.entity.City;
import com.example.bigCities.entity.Street;
import com.example.bigCities.repository.CityRepository;
import com.example.bigCities.repository.StreetRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class StreetService {
    private final StreetRepository streetRepository;
    private final CityRepository cityRepository;


    public StreetService(StreetRepository streetRepository, CityRepository cityRepository) {
        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
    }

    public List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    public Street getStreetById(Long id) {
        return streetRepository.findById(id)
                .orElseThrow();
    }

    public Street updateStreet(Long id, StreetDTO streetDTO) {
        Street street = streetRepository.findById(id)
                .orElseThrow();
        Long cityId = streetDTO.getCityId();
        if (cityId == null) {
            throw new IllegalArgumentException("City ID cannot be null");
        }
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        street.setName(streetDTO.getName());
        street.setCity(city);
        return streetRepository.save(street);
    }

    public void deleteStreet(Long id) {
        Street street = streetRepository.findById(id)
                .orElseThrow();
        streetRepository.delete(street);
    }


}
