package dimacm14.tests;

import dimacm14.models.login.LoginBodyModel;
import dimacm14.models.login.LoginResponseModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static dimacm14.specs.TestSpecs.bodyRequestSpec;
import static dimacm14.specs.TestSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("Login")
@Severity(BLOCKER)
@Owner("dimacm14")
@Feature("Авторизация")
public class LoginTests {

    @DisplayName("Авторизация с электронной почтой и паролем")
    @Test
    public void successfulLogin() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel loginResponse =
                step("При авторизации с электронной почтой и паролем ответ имеет код статуса 200", () ->
                        given(bodyRequestSpec)
                                .body(loginBody)
                                .when()
                                .post("/login")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(LoginResponseModel.class));

        step("Проверить токен", () ->
                assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    @DisplayName("Авторизация без пароля")
    @Test
    public void unsuccessfulMissingPasswordLogin() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        String expectedError = "Missing password";

        LoginResponseModel loginResponse =
                step("При авторизации без пароля ответ имеет код статуса 400", () ->
                        given(bodyRequestSpec)
                                .body(loginBody)
                                .when()
                                .post("/login")
                                .then()
                                .spec(responseSpec)
                                .statusCode(400)
                                .extract().as(LoginResponseModel.class));

        step("Получено сообщение об ошибке: " + expectedError, () ->
                assertThat(loginResponse.getError()).isEqualTo(expectedError));
    }
}
