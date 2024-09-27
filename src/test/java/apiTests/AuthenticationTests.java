package apiTests;

import apiTests.schema.LoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты на аутентификацию")
public class AuthenticationTests extends BaseTest {
    private final String registeredUser = "bat666";
    private final String unregisteredUser = "unregisteredUser";
    private final String validPassword = "password";
    private final String wrongPassword = "wrongPassword";

    @Test
    @DisplayName("Проверка аутентификации пользователя, зарегистрированного в системе")
    public void registeredUserTest() {
        Response response = getLoginResponse(registeredUser, validPassword);
        checkResponse(response, 200, true, null);
    }

    @Test
    @DisplayName("Проверка аутентификации незарегистрированного пользователя")
    public void unregisteredUserTest() {
        Response response = getLoginResponse(unregisteredUser, validPassword);
        checkResponse(response, 401, false, "Invalid credentials");
    }

    @Test
    @DisplayName("Проверка аутентификации зарегистрированного пользователя с неверным паролем")
    public void wrongPasswordTest() {
        Response response = getLoginResponse(registeredUser, wrongPassword);
        checkResponse(response, 401, false, "Invalid credentials");
    }

    private Response getLoginResponse(String username, String password) {
        return given(specification)
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post(loginPath);
    }

    private void checkResponse(Response response, int expectedStatusCode, boolean shouldHaveToken, String expectedMessage) {
        response.then().assertThat().statusCode(expectedStatusCode);

        if (shouldHaveToken) {
            response.then().body("access_token", notNullValue());
        } else {
            response.then().body("message", equalTo(expectedMessage));
        }
    }
}
