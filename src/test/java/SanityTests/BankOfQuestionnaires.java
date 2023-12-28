package SanityTests;

import Extensions.FileLogger;
import Utilities.CommonOperations;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import Extensions.ApiVerifications.BankOfQuestionnairesValidations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankOfQuestionnaires extends CommonOperations {

    public static String endPoint = "/api/bank_of_questionnaires";
    private static String randomId;

    @BeforeAll
    public static void setUp() {
        // Generate a randomId before all tests
        randomId = generateRandomId();
    }
    @Test
    @Order(1)
    public void testGetBankOfQuestionnaires() {
        // Positive test - Get the bank of questionnaires
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .when()
                .get(getData("api_url") + endPoint);

        FileLogger.info((response.prettyPrint()));

        // Validate the positive response
        response.then()
                .statusCode(200)
                .body("@type", equalTo("hydra:Collection"));

        // Extract the first item's ID for further testing
        String firstItemId = response.jsonPath().getString("'hydra:member'[0].id");

        // Positive test - Get a specific bank of questionnaire
        Response specificResponse = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .when()
                .get(getData("api_url") + endPoint + "/" + firstItemId);

        // Validate the positive specific response
        specificResponse.then()
                .statusCode(200)
                .body("@type", equalTo("BankOfQuestionnaire"))
                .body("@id", equalTo("/api/bank_of_questionnaires/" + firstItemId))
                .body("id", equalTo(firstItemId));


        // Negative test - Get a non-existing bank of questionnaire
        Response nonExistingResponse = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .when()
                .get(getData("api_url") + endPoint + "/non_existing_id");

        // Validate the negative response
        nonExistingResponse.then()
                .statusCode(400);
    }

    @Test
    @Order(2)
    public void testCreateBankOfQuestionnaires() {
        // Build the JSON request body using a HashMap
        Map<String, Object> typeObject = new HashMap<>();
        typeObject.put("value", 1);

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("id", randomId);
        requestBodyMap.put("type", typeObject);
        requestBodyMap.put("text", "text");

        // Convert the HashMap to JSON string
        String requestBody = new JSONObject(requestBodyMap).toString();

        // Send POST request
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .body(requestBody)
                .when()
                .post(getData("api_url") + endPoint);

        FileLogger.info((response.prettyPrint()));

        // Validate the response
        BankOfQuestionnairesValidations.assertSuccessCreateBankOfQuestionnairesContentMatch(response, randomId);

        // Negative test - Creating a bank of questionnaire and send with incorrect token
        // Send POST request
        Response incorrectTokenResponse = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiAdmin_token"))
                .body(requestBody)
                .when()
                .post(getData("api_url") + endPoint);
        // Validate the negative response
        incorrectTokenResponse.then()
                .statusCode(422);

        // Negative test - Creating a bank of questionnaire without the required 'text' field
        // Build the JSON request body for negative test (without 'text' field)
        requestBodyMap.clear();  // Clear the map for negative test
        requestBodyMap.put("id", randomId);
        requestBodyMap.put("type", typeObject);

        // Convert the HashMap to JSON string for negative test
        requestBody = new JSONObject(requestBodyMap).toString();

        // Send POST request for negative test
        Response negativeResponse = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiAdmin_token"))
                .body(requestBody)
                .when()
                .post(getData("api_url") + endPoint);

        // Validate the negative response
        negativeResponse.then()
                .statusCode(400);

    }
    @Test
    @Order(3)
    public void testGetBankOfQuestionnairesById() {
        // Positive test - Get a specific bank of questionnaire by ID
        Response getByIdResponse = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .when()
                .get(getData("api_url") + endPoint + "/" + randomId);

        FileLogger.info((getByIdResponse.prettyPrint()));

        // Validate the positive response
        getByIdResponse.then()
                .statusCode(200)
                .body("@type", equalTo("BankOfQuestionnaire"))
                .body("@id", equalTo("/api/bank_of_questionnaires/" + randomId))
                .body("id", equalTo(randomId));
    }

    @Test
    @Order(4)
    public void testGetBankOfQuestionnairesByIdNonExisting() {
        // Negative test - Get a non-existing bank of questionnaire by ID
        String nonExistingId = "e376d542-f1fd-466a-940a-9f07c4372787";

        Response nonExistingByIdResponse = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .when()
                .get(getData("api_url") + endPoint + "/" + nonExistingId);

        // Validate the negative response
        nonExistingByIdResponse.then()
                .statusCode(404);
    }

    @Test
    @Order(5)
    public void testDeleteBankOfQuestionnaires() {

        // Send DELETE request to delete the resource
        Response deleteResponse = given()
                .header("Authorization", "Bearer " + getData("apiBusiness_token"))
                .when()
                .delete(getData("api_url") + endPoint + "/" + randomId);

        // Validate the delete response
        deleteResponse.then()
                .statusCode(204);
    }
    private static String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}
