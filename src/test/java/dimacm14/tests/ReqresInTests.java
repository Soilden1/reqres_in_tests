package dimacm14.tests;

import dimacm14.models.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static dimacm14.specs.TestSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("reqresIn")
@Severity(BLOCKER)
@Owner("dimacm14")
public class ReqresInTests extends BaseTest {

    @Feature("Работа с пользователями")
    @DisplayName("Получить пользователя")
    @Test
    void getUserTest() {
        UserResponseModel user = step("Получть пользователя", () ->
                given(withoutBodyRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(UserResponseModel.class));

        step("Проверить пользовательские данные", () -> {
            assertEquals("janet.weaver@reqres.in", user.getData().getEmail());
            assertEquals("https://reqres.in/#support-heading", user.getSupport().getUrl());
        });
    }

    @Feature("Работа с пользователями")
    @DisplayName("Получить несуществующего пользователя")
    @Test
    void getNotExistUserTest() {
        step("Получить несуществующего пользователя", () ->
                given(withoutBodyRequestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(responseSpec)
                        .statusCode(404));
    }

    @Feature("Работа с пользователями")
    @DisplayName("Получить список пользователей")
    @Test
    void getUserUserTest() {
        UserListResponseModel userList = step("Получить список пользователей", () ->
                given(withoutBodyRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(UserListResponseModel.class));

        step("Проверить данные списка пользователей", () -> {
            assertEquals("2", userList.getPage());
            assertEquals("6", userList.getPerPage());
            assertEquals("Michael", userList.getData()[0].getFirstName());
        });
    }

    @Feature("Работа с пользователями")
    @DisplayName("Создать нового пользователя")
    @Test
    void createUserTest() {
        CreateUserBodyModel createUserBody = new CreateUserBodyModel();
        createUserBody.setName("morpheus");
        createUserBody.setJob("leader");

        CreateUserResponseModel user = step("Создать нового пользователя", () ->
                given(bodyRequestSpec)
                        .body(createUserBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверить пользовательские данные", () -> {
            assertEquals("morpheus", user.getName());
            assertEquals("leader", user.getJob());
        });
    }

    @Feature("Регистрация нового пользователя")
    @DisplayName("Зарегистрировать нового пользователя c валидными даными")
    @Test
    void successfulRegistrationTest() {
        RegistrationBodyModel registrationBody = new RegistrationBodyModel();
        registrationBody.setEmail("eve.holt@reqres.in");
        registrationBody.setPassword("pistol");

        RegistrationResponseModel registrationResponse =
                step("Зарегистрировать нового пользователя c валидными даными", () ->
                        given(bodyRequestSpec)
                                .body(registrationBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(RegistrationResponseModel.class));

        step("Проверить регистрационные данные", () ->
                assertEquals("QpwL5tke4Pnpja7X4", registrationResponse.getToken()));
    }

    @Feature("Регистрация нового пользователя")
    @DisplayName("Зарегистрировать нового пользователя без пароля")
    @Test
    void unsuccessfulMissingPasswordRegistrationTest() {
        RegistrationBodyModel registrationBody = new RegistrationBodyModel();
        registrationBody.setEmail("eve.holt@reqres.in");

        RegistrationResponseModel registrationResponse =
                step("Зарегистрировать нового пользователя без пароля", () ->
                        given(bodyRequestSpec)
                                .body(registrationBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(responseSpec)
                                .statusCode(400)
                                .extract().as(RegistrationResponseModel.class));

        step("Проверить сообщение об ошибке", () ->
                assertEquals("Missing password", registrationResponse.getError()));
    }
}
