package uz.pdp.appspringpcmarket.payload;

import lombok.Getter;

@Getter
public class ProductDto {

    private Integer categoryId;
    private String name;
    private float price;
    private String warranty;
    private boolean inStock;
    private boolean saleOnlyInComplete;

}
