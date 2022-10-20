package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringpcmarket.entity.Basket;
import uz.pdp.appspringpcmarket.entity.BasketProduct;

import java.util.List;

public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer> {

    List<BasketProduct> findAllByBasket(Basket basket);
}
