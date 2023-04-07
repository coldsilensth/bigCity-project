package com.example.bigCities.controller;

import com.example.bigCities.DTO.StreetDTO;
import com.example.bigCities.entity.Street;
import com.example.bigCities.service.CityService;
import com.example.bigCities.service.StreetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/street")
public class StreetController {
    private final CityService cityService;
    private final StreetService streetService;

    public StreetController(CityService cityService, StreetService streetService) {
        this.cityService = cityService;
        this.streetService = streetService;
    }

    //метод поиска улицы по id
    @GetMapping("/getStreetById/{id}")
    public Street getStreetById(@PathVariable Long id) {
        return streetService.getStreetById(id);
    }

    //метод который вытаскивает список всех улиц
    @GetMapping("/getAllStreet")
    public List<Street> getAllStreets() {
        return streetService.getAllStreets();
    }

    //метод обновления данных улицы по id
    @PutMapping("/updateStreet/{id}")
    public Street updateStreet(@PathVariable Long id, @RequestBody StreetDTO streetDTO) {
        return streetService.updateStreet(id, streetDTO);
    }

    //метод удаление улицы по id
    @DeleteMapping("/deleteStreet/{id}")
    public void deleteStreet(@PathVariable Long id) {
        streetService.deleteStreet(id);
    }

}
