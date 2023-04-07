package com.example.bigCities.controller;

import com.example.bigCities.DTO.ShopDTO;
import com.example.bigCities.DTO.ShopResultDTO;
import com.example.bigCities.entity.Shop;
import com.example.bigCities.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    //метод создания нового магазина
    @PostMapping("/create")
    public ResponseEntity<String> addShop(@RequestBody ShopDTO shopDTO) {
        Shop newShop = shopService.createNewShop(shopDTO);
        return ResponseEntity.ok("Ваш "+ newShop.getId() + " построенный магазин");

    }

    //метод поиска магазина по id
    @GetMapping("/{getShopById/id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        Shop shop = shopService.getShopById(id);
        return ResponseEntity.ok(shop);
    }

    //метод который вытаскивает список всех магазинов,
    // можем сортировать их по городу или улице
    @GetMapping("/allShops")
    public ResponseEntity<List<ShopResultDTO>> getShopByStreetOrCity(@RequestParam(name = "sort_by", defaultValue = "numberHome") String sortBy,
                                                                     @RequestParam(name = "sort_order", defaultValue = "asc") String sortOrder,
                                                                     @RequestParam(name = "city", required = false) String city,
                                                                     @RequestParam(name = "street", required = false) String street){
        List<ShopResultDTO> shopResultDTOS = shopService.getShopList(sortBy,sortOrder,city,street).getBody();
        return ResponseEntity.ok(shopResultDTOS);
    }

    //метод обновления магазина по id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateShop(@PathVariable Long id,@RequestBody ShopDTO shopDTO) {
        Shop updatedShop = shopService.updateShop(id, shopDTO);
        return ResponseEntity.ok(updatedShop);
    }

    //метод удаление магазина по id
    @DeleteMapping("/deleteShop/{id}")
    public void deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
    }

}
