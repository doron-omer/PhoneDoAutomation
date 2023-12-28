package Extensions;

import Utilities.CommonOperations;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import static Utilities.HelpersMethods.saveResponseToJson;

public class ApiActions extends CommonOperations {

    @Step("Get data from server")
    public static Response get(String paramsValues) {
        response = httpRequest.get(paramsValues);
        System.out.println(response.prettyPrint());
        return response;
    }

    @Step("Extract value from Json format ")
    public static String getFromJson(Response response)
    {
        jsonPath= response.jsonPath();
        System.out.println(response.getBody().prettyPrint());
        return jsonPath.toString();
    }

    @Step("Get api secured data using api query builder and save the response to a file")
    public static JsonElement getApiSecuredDataUsingQueryBuilder(String endpoint, String query, String fileName, String filePath) {
        String url = String.format("%s/%s?q=%s&token=%s&format=json", getData("endPoint"),
                endpoint, query, getData("apiAdmin_token"));
        Response response = RestAssured.given()
                .get(url);
        String responseBody = response.getBody().asString();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(responseBody);
        // Save the response to json file
        saveResponseToJson(responseBody, fileName, filePath);
        return jsonElement;
    }

    @Step("Get api data without key using api query builder and save the response to a file")
    public static JsonElement getApiDataUsingQueryBuilder(String query, String fileName, String filePath) {
        String url = String.format(query, getData("endPoint"));
        Response response = RestAssured.given().when()
                .get(url);
        String responseBody = response.getBody().asString();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(responseBody);
        // Save the response to json file
        saveResponseToJson(responseBody, fileName, filePath);
        return jsonElement;
    }
}
