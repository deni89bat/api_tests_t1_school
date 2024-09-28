package apiTests;

import apiTests.schema.Product;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsTests extends BaseTest {

    private static String productId;

    @BeforeAll
    public static void setUp() {
        List<Product> productsList = getProductsList();
        // Получаем ID первого продукта
        Product firstProduct = productsList.get(0);
        productId = String.valueOf(firstProduct.getId());
    }

    @Test
    @DisplayName("Проверка получения списка продуктов")
    public void getProductsListTest() {
        List<Product> productsList = getProductsList();
        assertFalse(productsList.isEmpty(), "Список продуктов пуст.");
    }

    @Test
    @DisplayName("Проверка поиска продукта по ID")
    public void searchProductByIdTest() {
        List<Product> product = getProductById(productId);
        assertFalse(product.isEmpty(), "Продукт не найден,в ответе пустой список []");
        assertEquals(Integer.parseInt(productId), product.get(0).getId(), "ID продукта в ответе не совпадает с запрошенным");
        assertEquals(1, product.size(), "В ответе вернулось более одного продукта");
    }

    @Test
    @DisplayName("Проверка поиска несуществующего продукта по ID")
    @Tag("Negative")
    public void getNonExistentProductByIdTest() {
        String nonExistentProductId = "666";

        given(specification)
                .when()
                .get(productsPath + "/" + nonExistentProductId)
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Product not found"));
    }

    private static List<Product> getProductsList() {
        return given(specification)
                .when()
                .get(productsPath)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().as(new TypeRef<List<Product>>() {
                });
    }

    private static List<Product> getProductById(String id) {
        return given(specification)
                .when()
                .get(productsPath + "/" + id)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().as(new TypeRef<List<Product>>() {
                });
    }
}
