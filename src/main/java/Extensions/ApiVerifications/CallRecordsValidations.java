package Extensions.ApiVerifications;

import Extensions.FileLogger;
import Utilities.CommonOperations;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
public class CallRecordsValidations extends CommonOperations {

    public static void assertSuccessWithCallRecordContentMatch(Response response) {
        response.then()
                .statusCode(200)
                .body("@type", equalTo("CallRecord"))
                .body("@id", equalTo("/api/call_records/0cac0303-da01-4844-8edd-cc58ce4cbbef"))
                .body("leadId", nullValue())
                .body("cnumber", nullValue())
                .body("snumber", nullValue())
                .body("snumberAlias", equalTo("Withheld"))
                .body("status", equalTo("not_answered"))
                .body("talktime", equalTo(0))
                .body("totaltime", equalTo(20))
                .body("hotLine", nullValue())
                .body("createdAt", equalTo("2023-12-21T11:50:01+00:00"))
                .body("direction", equalTo("incoming"))
                .body("lead", nullValue())
                .body("billings", empty())
                .body("isWithAudioRecord", equalTo(true));
    }

    public static void assertSuccessWithAdminCallRecordContentMatch(Response response) {
        response.then()
                .statusCode(200)
                .body("@type", equalTo("CallRecord"))
                .body("id", notNullValue())
                .body("cnumber", equalTo("+7636177"))
                .body("snumber", nullValue())
                .body("status", equalTo("answer"))
                .body("talktime", equalTo(2))
                .body("totaltime", equalTo(3))
                .body("createdAt", equalTo("2023-12-25T09:48:39+00:00"))
                .body("direction", equalTo("incoming"))
                .body("lead.clientInfoGivenName", equalTo("טעות"))
                .body("lead.callRecords.hotLine.companyName", hasItem("test version"))
                .body("isWithAudioRecord", equalTo(true));
    }

    public static void assertSuccessWithPresignedRecordContentMatch(Response response) {
        response.then()
                .statusCode(200)
                .body("@type", equalTo("CallRecord"))
                .body("signedUrl", startsWith("https://s3.amazonaws.com/phonedo-records-staging"))
                .body("period", notNullValue())
                .body("recordPath", notNullValue());
    }
    public static void assertSuccessWithTwoLegsRecordsContentMatch(Response response) {
        response.then()
                .statusCode(200)
                .body("@type", equalTo("CallRecord"))
                .body("@id", notNullValue())
                .body("inData.data.scustomer", equalTo("1451"))
                .body("inData.data.stype", equalTo("external"))
                .body("inData.data.status", equalTo(""))
                .body("inData.data.dnumber", notNullValue())
                .body("inData.data.callid", notNullValue())
                .body("inData.responses[0].message", equalTo("OK"))
                .body("inData.responses[0].key", equalTo(""))
                .body("inData.responses[0].code", equalTo("200"))
                .body("outData.data.scustomer", equalTo("1451"))
                .body("outData.data.stype", equalTo("external"))
                .body("outData.data.status", equalTo(""))
                .body("outData.data.dnumber", notNullValue())
                .body("outData.data.callid", notNullValue())
                .body("outData.responses[0].message", equalTo("OK"))
                .body("outData.responses[0].key", equalTo(""))
                .body("outData.responses[0].code", equalTo("200"));
    }
}
