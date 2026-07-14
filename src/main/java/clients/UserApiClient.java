package clients;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import models.UserCreds;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru";
    private static final String API_AUTH_REGISTER = "/api/auth/register";
    private static final String API_AUTH_LOGIN = "/api/auth/login";
    private static final String API_AUTH_USER = "/api/auth/user";

    private final RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

    public UserApiClient() {
    }
    @Step("Создать пользователя")
    public Response createUser(User user) {
        return given().spec(reqSpec)
                .body(user)
                .when()
                .post(API_AUTH_REGISTER);
    }

    @Step("Авторизовать пользователя")
    public Response loginUser(UserCreds creds) {
        return given().spec(reqSpec)
                .body(creds)
                .when()
                .post(API_AUTH_LOGIN);
    }

    @Step("Изменить данные пользователя")
    public Response updateUser(User updatedData, String token) {
        RequestSpecification request = given().spec(reqSpec);
        if (token != null) {
            request.header("Authorization", token);
        }
        return request.body(updatedData)
                .when()
                .patch(API_AUTH_USER);
    }

    @Step("Удалить пользователя")
    public void deleteUser(String token) {
        RequestSpecification request = given().spec(reqSpec);
        if (token != null) {
            request.header("Authorization", token);
        }
        request.when()
                .delete(API_AUTH_USER);
    }

}
