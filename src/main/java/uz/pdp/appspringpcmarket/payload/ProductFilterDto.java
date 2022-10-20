package uz.pdp.appspringpcmarket.payload;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductFilterDto {

    private float minPrice;
    private float maxPrice;
    List<Integer> propertyIds;

}
