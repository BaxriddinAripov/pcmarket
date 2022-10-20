package uz.pdp.appspringpcmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringpcmarket.entity.Basket;
import uz.pdp.appspringpcmarket.payload.ApiResponse;
import uz.pdp.appspringpcmarket.payload.BasketDto;
import uz.pdp.appspringpcmarket.payload.BasketResponse;
import uz.pdp.appspringpcmarket.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProductToBasket(@RequestParam(required = false) Integer basketId, @RequestParam Integer productId, @RequestParam(defaultValue = "1") Integer amount){
        BasketResponse basketResponse = basketService.addProductToBasket(basketId, productId, amount);
        return ResponseEntity.status(basketResponse.isSuccess()?201:409).body(basketResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllBaskets(){
        List<Basket> allBaskets = basketService.getAllBaskets();
        return ResponseEntity.status(allBaskets.isEmpty()?204:200).body(allBaskets);
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> getBasketById(@PathVariable Integer id){
        Basket basket = basketService.getBasketById(id);
        return ResponseEntity.status(basket==null?204:200).body(basket);
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProductFromBasket(@RequestParam Integer basketId, @RequestParam Integer productId){
        ApiResponse apiResponse =basketService.deleteProductFromBasket(basketId,productId);
        return ResponseEntity.status(apiResponse.isSuccess()?204:409).body(apiResponse);
    }

    @PostMapping("/{basketId}")
    public ResponseEntity<?> createOrder(@PathVariable Integer basketId, @RequestBody BasketDto basketDto){
        ApiResponse apiResponse = basketService.createOrder(basketId, basketDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @DeleteMapping("/{basketId}")
    public ResponseEntity<?> deleteBasket(@PathVariable Integer basketId){
        ApiResponse apiResponse = basketService.deleteBasket(basketId);
        return ResponseEntity.status(apiResponse.isSuccess()?204:409).body(apiResponse);
    }
}
