package apiTests.schema;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddInCartRequest {
    @JsonProperty("product_id")
    int productId;
    int quantity;
}
