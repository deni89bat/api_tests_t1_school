package apiTests.schema;

import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(force = true)
@Value
public class Cart {
    int id;
    String name;
    String category;
    double price;
    Double discount;
    int quantity;
}



