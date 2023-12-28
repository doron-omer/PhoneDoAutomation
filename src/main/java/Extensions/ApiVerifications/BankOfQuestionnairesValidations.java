package Extensions.ApiVerifications;

import Utilities.CommonOperations;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class BankOfQuestionnairesValidations extends CommonOperations {

    public static void assertSuccessCreateBankOfQuestionnairesContentMatch(Response response, String randomId) {
        response.then()
                .statusCode(201)
                .body("@type", equalTo("BankOfQuestionnaire"))
                .body("@id", equalTo("/api/bank_of_questionnaires/" + randomId))
                .body("id", equalTo(randomId))
                .body("text", equalTo("text"))
                .body("type", equalTo(1));
    }
}
