package jira.api.homework;

import org.testng.annotations.Test;

import base.BaseTestSetup;
import io.restassured.response.Response;

import static Utils.Constants.BASE_URL;
import static Utils.Constants.BUG_KEY;
import static Utils.Constants.BUG_PRIORITY;
import static Utils.Endpoints.ISSUE_ENDPOINT;
import static Utils.Helper.isValid;
import static Utils.JSONRequests.PRIORITY;
import static io.restassured.RestAssured.baseURI;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PriorityTest extends BaseTestSetup {

    @Test
    public void setBugPriorityTest() {

        baseURI = format("%s%s/%s", BASE_URL, ISSUE_ENDPOINT, BUG_KEY);

        String requestBody = (format(PRIORITY, BUG_PRIORITY));
        assertTrue(isValid(requestBody), "Body is not a valid JSON");

        Response response = getApplicationJSONSpecification()
            .body(requestBody)
            .put();

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SC_NO_CONTENT, "Incorrect status code. Expected 204.");
        assertEquals(response.body().asString(), "", "Response body is not empty");

        System.out.printf("Priority of bug with key %s was set to %s%n", BUG_KEY, BUG_PRIORITY);
    }
}
