package dimacm14.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = System.getProperty("baseUrl", "https://reqres.in/");
        RestAssured.basePath = "/api";
    }
}
