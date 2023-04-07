package com.example.bigCities.service;

import com.example.bigCities.DTO.ShopDTO;
import com.example.bigCities.DTO.ShopResultDTO;
import com.example.bigCities.entity.City;
import com.example.bigCities.entity.Shop;
import com.example.bigCities.entity.Street;
import com.example.bigCities.repository.CityRepository;
import com.example.bigCities.repository.ShopRepository;
import com.example.bigCities.repository.StreetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;
    private final int SIZE = 6;


    public ShopService(ShopRepository shopRepository, CityRepository cityRepository, StreetRepository streetRepository) {
        this.shopRepository = shopRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
    }

    public Shop getShopById(Long id) {
        return shopRepository.findById(id).orElseThrow();
    }
    public Shop updateShop(Long id, ShopDTO shopDTO) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow();
        City city = cityRepository.findById(shopDTO.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        Street street = streetRepository.findById(shopDTO.getStreetId())
                .orElseThrow(() -> new EntityNotFoundException("Street not found"));

        shop.setCity(city);
        shop.setStreet(street);
        shop.setNumberHome(shopDTO.getNumberHome());
        shop.setOpeningTime(shopDTO.getOpeningTime());
        shop.setClosingTime(shopDTO.getClosingTime());
        return shopRepository.save(shop);
    }

    public Shop createNewShop(ShopDTO shopDTO) {
        City city = cityRepository.findById(shopDTO.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("City not found"));
        Street street = streetRepository.findById(shopDTO.getStreetId())
                .orElseThrow(() -> new EntityNotFoundException("Street not found"));

        Shop shop = new Shop();
        shop.setCity(city);
        shop.setStreet(street);
        shop.setNumberHome(shopDTO.getNumberHome());
        shop.setOpeningTime(shopDTO.getOpeningTime());
        shop.setClosingTime(shopDTO.getClosingTime());
        shopRepository.save(shop);
        return shop;
    }

    private boolean isShopOpen(LocalTime openingTime, LocalTime closingTime) {
        LocalTime now = LocalTime.now();
        if (openingTime.isBefore(closingTime)) {
            return now.isAfter(openingTime) && now.isBefore(closingTime);
        } else {
            return now.isAfter(openingTime) || now.isBefore(closingTime);
        }
    }

    public ResponseEntity<List<ShopResultDTO>> getShopList(String sortBy, String sortOrder, String city, String street) {
        int page = 0;
        int size = 6;
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Shop> shopPage;

        if (city != null && street != null) {
            City cityObj = cityRepository.findByName(city);
            Street streetObj = streetRepository.findByName(street);
            shopPage = shopRepository.findAllByCityAndStreet(cityObj, streetObj, pageable);
        } else if (city != null) {
            City cityObject = cityRepository.findByName(city);
            shopPage = shopRepository.findAllByCity(cityObject, pageable);
        } else if (street != null) {
            Street streetObj = streetRepository.findByName(street);
            shopPage = shopRepository.findAllByStreet(streetObj, pageable);
        } else {
            shopPage = shopRepository.findAll(pageable);
        }

        List<ShopResultDTO> shopResultDTOSs = new ArrayList<>();
        for (Shop shop : shopPage) {
            ShopResultDTO shopResultDTO = new ShopResultDTO();
            shopResultDTO.setCity(shop.getStreet().getCity().getName());
            shopResultDTO.setStreet(shop.getStreet().getName());
            shopResultDTO.setNumberHome(shop.getNumberHome());
            shopResultDTO.setOpen(isShopOpen(shop.getOpeningTime(), shop.getClosingTime()));
            shopResultDTOSs.add(shopResultDTO);
        }
        return ResponseEntity.ok(shopResultDTOSs);
    }

    public void deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow();
        shopRepository.delete(shop);
    }

}
