package com.example.bigCities.DTO;

import lombok.Data;

@Data
public class StreetDTO {
    private Long id;
    private String name;
    private Long cityId;
    public StreetDTO(Long id, String name, Long cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
    }

}
