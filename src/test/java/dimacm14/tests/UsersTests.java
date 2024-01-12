package dimacm14.tests;

import dimacm14.models.createUser.CreateUserBodyModel;
import dimacm14.models.createUser.CreateUserResponseModel;
import dimacm14.models.user.UpdateUserResponseModel;
import dimacm14.models.user.UserListResponseModel;
import dimacm14.models.user.UserResponseModel;
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
import static org.assertj.core.api.Assertions.assertThat;

@Tag("User")
@Severity(BLOCKER)
@Owner("dimacm14")
@Feature("Работа с пользователями")
public class UsersTests extends BaseTest {

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
            assertThat(user.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(user.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        });
    }

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
            assertThat(userList.getPage()).isEqualTo("2");
            assertThat(userList.getPerPage()).isEqualTo("6");
            assertThat(userList.getData()[0].getFirstName()).isEqualTo("Michael");
        });
    }

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
            assertThat(user.getName()).isEqualTo("morpheus");
            assertThat(user.getJob()).isEqualTo("leader");
        });
    }

    @DisplayName("Обновить данные пользователя")
    @Test
    public void putUpdateUser() {
        CreateUserBodyModel updateUserBody = new CreateUserBodyModel();
        updateUserBody.setName("morpheus");
        updateUserBody.setJob("zion resident");

        UpdateUserResponseModel updateUserResponse =
                step("При обновлении данных пользователя ответ имеет код статуса 200", () ->
                        given(bodyRequestSpec)
                                .body(updateUserBody)
                                .when()
                                .put("/users/2")
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(UpdateUserResponseModel.class));

        step("Проверить данные пользователя", () -> {
            assertThat(updateUserResponse.getName()).isEqualTo("morpheus");
            assertThat(updateUserResponse.getJob()).isEqualTo("zion resident");
        });
    }
}
