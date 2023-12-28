package Extensions.ApiVerifications;

import Utilities.CommonOperations;

import Utilities.CommonOperations;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
public class AvailableTelephonesValidations extends CommonOperations {

    public static void assertSuccessCreateAvailableTelephoneContentMatch(Response response, String randomId) {
        response.then()
                .statusCode(201)
                .body("@id",equalTo("/api/available_telephones/" + randomId))
                .body("@type", equalTo("AvailableTelephone"))
                .body("purchasedTelephoneRef", nullValue())
                .body("allocatedAt", nullValue())
                .body("disconnectedAt", nullValue())
                .body("id", equalTo(randomId))
                .body("telephone", equalTo("+15819207086"))
                .body("price.amount", equalTo("300"))
                .body("price.currency", equalTo("USD"))
                .body("price.formatted", equalTo("$3.00"))
                .body("price.symbol", equalTo("$"))
                .body("country", equalTo("US"));
    }
}
