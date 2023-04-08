package com.example.bigCities.DTO;

import lombok.Data;

@Data
public class ShopResultDTO {
    private String city;
    private String street;
    private int numberHome;
    private boolean isOpen;
}
