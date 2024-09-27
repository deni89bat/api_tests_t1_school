package apiTests.schema;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    private String category;
    private Double discount;
    private int id;
    private String name;
    private double price;
}
