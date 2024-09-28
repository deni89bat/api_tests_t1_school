package apiTests;

import apiTests.schema.AddInCartRequest;
import apiTests.schema.GetCartResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartTests extends BaseTest {
    private static String TOKEN;

    @BeforeAll
    public static void setUp() {
        TOKEN = getValidAuthToken("bat666", "password");
        assertNotNull(TOKEN, "Токен не получен, авторизация не удалась.");
    }

    @Test
    @DisplayName("Проверка добавления существующего продукта в корзину клиента")
    public void addProductToCartTest() {

        given(specification)
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(ContentType.JSON)
                .body(new AddInCartRequest(1, 1))
                .when()
                .post(cartPath)
                .then()
                .statusCode(201)
                .body("message", equalTo("Product added to cart successfully"));
    }

    @Test
    @DisplayName("Проверка добавления несуществующего продукта в корзину клиента")
    @Tag("Negative")
    public void addNonExistentProductToCartTest() {
        given(specification)
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(ContentType.JSON)
                .body(new AddInCartRequest(9999, 1))  // Не существующий product_id
                .when()
                .post(cartPath)
                .then()
                .statusCode(404)
                .body("message", equalTo("Product not found"));
    }

    @Test
    @DisplayName("Проверка добавления продукта в корзину клиента, если клиент не авторизован")
    @Tag("Negative")
    public void addProductToCartWithoutAuthorizationTest() {
        given(specification)
                .header("Authorization", "Bearer + wrong_token")
                .contentType(ContentType.JSON)
                .body(new AddInCartRequest(1, 1))  // Существующий product_id
                .when()
                .post(cartPath)
                .then()
                //Сделал в соответствии с заданием, по факту при указании неверного токена приходит 422. В случае если совсем не указать заголовок Authorization то придёт 401.
                .statusCode(401)
                .body("msg", equalTo("Missing Authorization Header"));
    }

    @Test
    @DisplayName("Проверка получения корзины клиента")
    public void getCartTest() {
        given(specification)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get(cartPath)
                .then()
                .statusCode(200)
                .extract().as(GetCartResponse.class);
    }

    @Test
    @DisplayName("Проверка удаления продукта, который был добавлен ранее в корзину")
    public void removeProductFromCartTest() {
        int productId = 1;
        given(specification)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .delete(cartPath + "/" + productId)
                .then()
                .statusCode(200)
                .body("message", equalTo("Product removed from cart"));
    }

    @Test
    @DisplayName("Проверка удаления продукта, который не был добавлен в корзину клиента")
    @Tag("Negative")
    public void removeNonExistentProductFromCartTest() {
        given(specification)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .delete(cartPath + "/2")
                .then()
                .statusCode(404)
                .body("message", equalTo("Product not found in cart"));
    }

}
