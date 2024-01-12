package dimacm14.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static dimacm14.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class TestSpecs {

    public static RequestSpecification withoutBodyRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().uri();

    public static RequestSpecification bodyRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");;

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
