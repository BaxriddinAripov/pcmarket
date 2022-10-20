package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appspringpcmarket.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query( value = "select p from product p  join product_characters pc on p.id = pc.product_id " +
            "join chars c on pc.characters_id = c.id\n" +
            "join character_property cp on c.id = cp.characters_id " +
            "join property pr on pr.id = cp.property_id " +
            "where pr.id in  :idList and p.price> :minPrice and p.price< :maxPrice", nativeQuery = true)
    List<Product> findAllByFilter(@Param("idList") List<Integer> idList,
                                  @Param("minPrice") float minPrice,
                                  @Param("maxPrice")float maxPrice);

}
