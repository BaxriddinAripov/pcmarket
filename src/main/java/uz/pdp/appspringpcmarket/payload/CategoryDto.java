package uz.pdp.appspringpcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {

    @NotNull(message = "name bo'sh bo'masligi kerak")
    private String name;

    @NotNull(message = "parentCategoryId bo'sh bo'masligi kerak")
    private Integer parentCategoryId;
}
