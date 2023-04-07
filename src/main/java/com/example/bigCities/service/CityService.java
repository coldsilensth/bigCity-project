package com.example.bigCities.service;

import com.example.bigCities.DTO.CityDTO;
import com.example.bigCities.DTO.StreetDTO;
import com.example.bigCities.entity.City;
import com.example.bigCities.entity.Street;
import com.example.bigCities.repository.CityRepository;
import com.example.bigCities.repository.StreetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;


    public CityService(CityRepository cityRepository, StreetRepository streetRepository) {
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public City upadteCity(Long id, CityDTO cityDTO) {
        Optional<City> cityId = cityRepository.findById(id);
        if (cityId.isPresent()) {
            City city1 = cityId.get();
            city1.setName(cityDTO.getName());
            return cityRepository.save(city1);
        }else {
            throw new EntityNotFoundException("City not found with id: " + id);
        }
    }

    public City createCity(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        return cityRepository.save(city);
    }

    public ResponseEntity<String> addStreetsToCity(Long cityId, StreetDTO streetDTO) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        Street street = new Street();
        street.setName(streetDTO.getName());
        street.setCity(city);
        city.getStreets().add(street);
        cityRepository.save(city);
        return ResponseEntity.ok("Street added to city");
    }


    public List<StreetDTO> getAllStreetsInCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        List<Street> streets = city.getStreets();
        List<StreetDTO> streetDTOList = new ArrayList<>();

        for (Street street : streets) {
            streetDTOList.add(new StreetDTO(street.getId(), street.getName(), cityId));
        }
        return streetDTOList;
    }

    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow();
        cityRepository.delete(city);
    }
}
