package com.example.bigCities.controller;


import com.example.bigCities.DTO.CityDTO;
import com.example.bigCities.DTO.StreetDTO;
import com.example.bigCities.entity.City;
import com.example.bigCities.repository.CityRepository;
import com.example.bigCities.repository.StreetRepository;
import com.example.bigCities.service.CityService;
import com.example.bigCities.service.StreetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    //метод создания нового города
    @PostMapping("/newCity")
    public ResponseEntity<String> addCity(@RequestBody CityDTO cityDTO) {
        City city = cityService.createCity(cityDTO);
        return ResponseEntity.ok("Город " + city.getName() + " добавлен! Его ID " + city.getId());
    }

    //метод поиска города по id
    @GetMapping("/getCityById/{id}")
    public Optional<City> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    //метод который вытаскивает список всех городов
    @GetMapping("/allCity")
    public List<City> getAllCity() {
        return cityService.getAllCities();
    }

    //метод изменения названия города по id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCity(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        City updatedCity = cityService.upadteCity(id, cityDTO);
        return ResponseEntity.ok(updatedCity);
    }

    //метод создания новой улицы и добавления в город
    @PostMapping("/{cityId}/streets")
    public ResponseEntity<Optional<City>> addStreetsToCity(@PathVariable Long cityId, @RequestBody StreetDTO streetDTO) {
        cityService.addStreetsToCity(cityId, streetDTO);
        return ResponseEntity.ok().build();
    }

    //метод который вытаскивает список всех улиц в этом городе
    @GetMapping("/{cityId}/streetsAll")
    public List<StreetDTO> getAllStreetsInCity(@PathVariable Long cityId) {
        return cityService.getAllStreetsInCity(cityId);
    }

    //метод удаление города по id
    @DeleteMapping("/deleteCity/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
