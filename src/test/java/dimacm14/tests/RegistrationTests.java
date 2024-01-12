package dimacm14.tests;

import dimacm14.models.registration.RegistrationBodyModel;
import dimacm14.models.registration.RegistrationResponseModel;
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

@Tag("Registration")
@Severity(BLOCKER)
@Owner("dimacm14")
@Feature("Регистрация нового пользователя")
public class RegistrationTests extends BaseTest {

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
                assertThat(registrationResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

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
                assertThat(registrationResponse.getError()).isEqualTo("Missing password"));
    }
}
