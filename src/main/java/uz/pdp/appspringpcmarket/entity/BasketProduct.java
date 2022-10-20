package uz.pdp.appspringpcmarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BasketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    private Basket basket;

    @ManyToOne
    private Product product;

    private Integer amount;

    public BasketProduct(Basket basket, Product product, Integer amount) {
        this.basket = basket;
        this.product = product;
        this.amount = amount;
    }
}
