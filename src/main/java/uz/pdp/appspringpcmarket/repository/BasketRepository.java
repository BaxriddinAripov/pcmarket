package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringpcmarket.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
