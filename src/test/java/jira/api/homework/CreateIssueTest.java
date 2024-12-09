package jira.api.homework;

import org.testng.annotations.Test;

import base.BaseTestSetup;
import dataproviders.IssueFields;
import io.restassured.response.Response;

import static Utils.Constants.BASE_URL;
import static Utils.Constants.BUG_DESCRIPTION;
import static Utils.Constants.BUG_KEY;
import static Utils.Constants.BUG_NAME;
import static Utils.Constants.BUG_SUMMARY;
import static Utils.Constants.PROJECT_KEY;
import static Utils.Constants.STORY_KEY;
import static Utils.Constants.STORY_MULTILINE_DESCRIPTION;
import static Utils.Constants.STORY_NAME;
import static Utils.Constants.STORY_SUMMARY;
import static Utils.Endpoints.ISSUE_ENDPOINT;
import static Utils.Helper.isValid;
import static Utils.JSONRequests.ISSUE;
import static io.restassured.RestAssured.baseURI;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateIssueTest extends BaseTestSetup {

    @Test(priority = 1)
    public void createStoryTest() {

        baseURI = format("%s%s", BASE_URL, ISSUE_ENDPOINT);

        String requestBody = (format(ISSUE, PROJECT_KEY, STORY_SUMMARY, STORY_MULTILINE_DESCRIPTION, STORY_NAME));
        assertTrue(isValid(requestBody), "Body is not a valid JSON");

        Response response = getApplicationJSONSpecification()
            .body(requestBody)
            .post();

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SC_CREATED, "Incorrect status code. Expected 201.");

        STORY_KEY = response.getBody().jsonPath().get("key");

        System.out.printf("Story with key %s was created%n%n", STORY_KEY);
    }

    @Test(priority = 2)
    public void createBugTest() {

        baseURI = format("%s%s", BASE_URL, ISSUE_ENDPOINT);

        String requestBody = (format(ISSUE, PROJECT_KEY, BUG_SUMMARY, BUG_DESCRIPTION, BUG_NAME));
        assertTrue(isValid(requestBody), "Body is not a valid JSON");

        Response response = getApplicationJSONSpecification()
            .body(requestBody)
            .post();

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SC_CREATED, "Incorrect status code. Expected 201.");

        BUG_KEY = response.getBody().jsonPath().get("key");

        System.out.printf("Bug with key %s was created%n%n", BUG_KEY);
    }
}
