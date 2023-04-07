package com.example.bigCities.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CityDTO {
    private Long id;
    private String name;
    private List<StreetDTO> streets = new ArrayList<>();

}
