package com.example.bigCities.DTO;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ShopDTO {
    private Long id;
    private Long cityId;
    private Long streetId;
    private int numberHome;
    private LocalTime closingTime;
    private LocalTime openingTime;
    private boolean isOpen;
}
