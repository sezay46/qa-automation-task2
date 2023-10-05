package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class CreateNewCar {

    public ValidatableResponse validatableResponse;
    public final String endpoint = "https://5d67b6b46847d40014f663fc.mockapi.io/api/cars";
    public static JSONObject requestBodyJson = new JSONObject();

    @When("the user sent the post request")
    public void requestSentPost() {
        requestBodyJson.put("manufacturer", "Audi");
        requestBodyJson.put("model", "A5");

        validatableResponse = given().log().all().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(requestBodyJson.toString()).
                post(endpoint).
                then().log().all();
    }

    @Then("he receive the response and verify it")
    public void responseFromPostRequest() {
        validatableResponse.assertThat().statusCode(201);
        validatableResponse.assertThat().contentType(ContentType.JSON);
        validatableResponse.assertThat().body("manufacturer", equalTo("Audi"));
        validatableResponse.assertThat().body("model", equalTo("A5"));
        validatableResponse.assertThat().body(hasItem("id"));
    }

    @When("the user sent the get request")
    public void requestSentGet() {
        String manufacturer = requestBodyJson.get("manufacturer").toString();

        validatableResponse = given().log().all().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                queryParam("manufacturer", manufacturer).
                get(endpoint).
                then().log().all();
    }

    @Then("he receive the response and verify")
    public void responseFromGetRequest() {
        validatableResponse.assertThat().statusCode(200);
        validatableResponse.assertThat().body("manufacturer", equalTo("Audi"));
        validatableResponse.assertThat().body("model", equalTo("A5"));
        validatableResponse.assertThat().body(hasItem("id"));
    }
}
