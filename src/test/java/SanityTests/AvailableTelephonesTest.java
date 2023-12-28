package SanityTests;

import Extensions.FileLogger;
import Utilities.CommonOperations;
import Extensions.ApiVerifications.AvailableTelephonesValidations;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class AvailableTelephonesTest extends CommonOperations {

    public static String endPoint = "/api/available_telephones";
    @Test
    public void testCreateAvailableTelephone() {
        String randomId = generateRandomId();
        String randomTelephone = generateRandomTelephone();
        String randomAmount = generateRandomAmount();

        String userToken = getData("apiAdmin_token");

// Build the JSON request body using a HashMap
        Map<String, Object> priceObject = new HashMap<>();
        priceObject.put("amount", randomAmount);
        priceObject.put("currency", "USD");
        priceObject.put("formatted", "$" + randomAmount + ".00");
        priceObject.put("symbol", "$");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("@id", "/api/available_telephones/" + randomId);
        requestBodyMap.put("@type", "AvailableTelephone");
        requestBodyMap.put("purchasedTelephoneRef", null);
        requestBodyMap.put("allocatedAt", null);
        requestBodyMap.put("disconnectedAt", null);
        requestBodyMap.put("id", randomId);
        requestBodyMap.put("telephone", randomTelephone);
        requestBodyMap.put("price", priceObject);
        requestBodyMap.put("country", "US");

// Convert the HashMap to JSON string
        String requestBody = new JSONObject(requestBodyMap).toString();

// Log the requestBody for debugging
        System.out.println("Request Body: " + requestBody);


        // Print the equivalent CURL command
        String curlCommand = String.format("curl -X POST \"%s\" -H 'Accept: application/ld+json' -H 'Content-Type: application/ld+json' -H 'Authorization: Bearer %s' -d '%s'", getData("api_url") + endPoint, userToken, requestBody);
        FileLogger.info(curlCommand);

        // Send POST request
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Content-Type", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .post(getData("api_url") + endPoint);

        FileLogger.info((response.prettyPrint()));

        // Validate the response
        AvailableTelephonesValidations.assertSuccessCreateAvailableTelephoneContentMatch(response, randomId);
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    private String generateRandomTelephone() {
        return "+1" + (100_000_0000 + Math.round(Math.random() * 1_000_000_000));
    }

    private String generateRandomAmount() {
        return String.valueOf((int) (Math.random() * 500) + 100);
    }
}
