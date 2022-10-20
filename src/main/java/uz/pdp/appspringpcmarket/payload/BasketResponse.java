package uz.pdp.appspringpcmarket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponse {

    private String message;
    private boolean success;
    private Integer basketId;

}
