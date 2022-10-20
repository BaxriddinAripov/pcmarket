package uz.pdp.appspringpcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "basket")
    private List<BasketProduct> basketProducts=new ArrayList<>();

    private String phoneNumber;
    private String email;
    private String name;
    private String address;
    private String geolocation;

    private boolean isBasket;
}
