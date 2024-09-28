package apiTests.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@NoArgsConstructor(force = true)
@Value
public class GetCartResponse {
    List<Cart> cart;

    @JsonProperty("total_price")
    double totalPrice;

    @JsonProperty("total_discount")
    Double totalDiscount;
}
