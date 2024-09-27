package apiTests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.*;

public class BaseTest {
    public static String loginPath = "/login";
    public static String productsPath = "/products";
    public static String cartPath = "/cart";

    public static final RequestSpecification specification = given()
            .baseUri("http://9b142cdd34e.vps.myjino.ru:49268")
            //.contentType(ContentType.JSON)
            .accept(ContentType.JSON);
    static {
        RestAssured.useRelaxedHTTPSValidation();
        filters(new RequestLoggingFilter(System.out), new ResponseLoggingFilter(System.out));
    }


}


