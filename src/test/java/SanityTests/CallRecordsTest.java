package SanityTests;

import Extensions.ApiVerifications.CallRecordsValidations;
import Extensions.FileLogger;
import Utilities.CommonOperations;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CallRecordsTest extends CommonOperations {

    public static String endPoint = "/api/call_records";
    public static String adminEndPoint = "/api/admin/call_records";

    //Test the api/call_records
    @Test
    public void testFetchCallRecords() {
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint);
        response.then().statusCode(200);
        response.then().body("'hydra:member'[0].businessName", equalTo("test version"));
    }

    @Test
    public void testInvalidCallRecordsRequest() {
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") +"/api/call_record");
        response.then().statusCode(404);
    }

    //Test the api/call_records/{id}
    @Test
    public void testFetchCallRecordById() {
        String id = "0cac0303-da01-4844-8edd-cc58ce4cbbef";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint + "/" + id);

        response.then().statusCode(200);
        CallRecordsValidations.assertSuccessWithCallRecordContentMatch(response);
    }

    @Test
    public void testInvalidSingleRecordRequest() {
        String id = "0aa3327a-cd82-11ea-bf0a-f9af7ef54097";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint + "/" + id);

        response.then().statusCode(404);
    }

    //Test the api/admin/call_records/{id}
    @Test
    public void testPositiveFetchAdminCallRecord() {
        String recordId = "ab6537d8-25b1-4478-b03b-3199b18fbc5b";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + adminEndPoint + "/" + recordId);
        //FileLogger.info(response.prettyPrint());
        CallRecordsValidations.assertSuccessWithAdminCallRecordContentMatch(response);
    }

    @Test
    public void testNegativeFetchRecordNotFound() {
        String nonExistentRecordId = "ab6537d8-25b1-4478-b03b-3199b18fbc5c";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + adminEndPoint + "/" + nonExistentRecordId);

        response.then()
                .statusCode(404);
    }

    //Test the api/call_records/{id}/presigned_record
    @Test
    public void testGetPresignedRecordSuccess() {
        String recordId = "0cac0303-da01-4844-8edd-cc58ce4cbbef";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint + "/" + recordId + "/presigned_record");

        CallRecordsValidations.assertSuccessWithPresignedRecordContentMatch(response);
    }

    @Test
    public void testGetPresignedRecordNotFound() {
        String nonExistentRecordId = "0cac0303-da01-4844-8edd-cc58ce4cbbed";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint + "/" + nonExistentRecordId + "/presigned_record");

        response.then()
                .statusCode(404);
    }

    //Test the api/call_records/{id}/two_legs_records
    @Test
    public void testGetTwoLegsRecordsSuccess() {
        String recordId = "0cac0303-da01-4844-8edd-cc58ce4cbbef";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint + "/" + recordId + "/two_legs_records");

        CallRecordsValidations.assertSuccessWithTwoLegsRecordsContentMatch(response);
    }

    @Test
    public void testGetTwoLegsRecordsNotFound() {
        String nonExistentRecordId = "0cac0303-da01-4844-8edd-cc58ce4cbbed";
        String userToken = getData("apiAdmin_token");
        Response response = given()
                .header("Accept", "application/ld+json")
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get(getData("api_url") + endPoint + "/" + nonExistentRecordId + "/two_legs_records");

        response.then()
                .statusCode(404);
    }

}

