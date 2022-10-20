package uz.pdp.appspringpcmarket.payload;

import lombok.Getter;

@Getter
public class BasketDto {
    private String phoneNumber;
    private String email;
    private String name;
    private String address;
    private String geolocation;
    private boolean isBasket;
}
