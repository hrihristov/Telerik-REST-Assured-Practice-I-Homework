package base;

import java.lang.reflect.Method;

import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static Utils.Constants.ACCOUNT_ID;
import static Utils.Constants.BASE_URL;
import static Utils.Constants.BUG_KEY;
import static Utils.Constants.BUG_NAME;
import static Utils.Constants.DEFAULT_ACCOUNT_ID;
import static Utils.Constants.EMAIL;
import static Utils.Constants.JIRA_API_TOKEN;
import static Utils.Constants.PROJECT_KEY;
import static Utils.Constants.STORY_KEY;
import static Utils.Constants.STORY_NAME;
import static Utils.Endpoints.SEARCH_ENDPOINT;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;

public class BaseTestSetup implements ITest {

    private static ThreadLocal<String> TEST_NAME = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        if (STORY_KEY == null) {
            STORY_KEY = getLatestIssueKeyByType(STORY_NAME);
        }

        if (BUG_KEY == null) {
            BUG_KEY = getLatestIssueKeyByType(BUG_NAME);
        }

        if (ACCOUNT_ID == null) {
            ACCOUNT_ID = DEFAULT_ACCOUNT_ID;
        }
    }

    /**
     * Method is called before each test with group dataProvider. It set the name of the test and enables logging of both the request and the response
     * if REST Assureds test validation fails with the specified log detail.
     *
     * @param method   test case method
     * @param testData test data from Data Provider
     */
    @BeforeMethod(alwaysRun = true, onlyForGroups = "dataProvider")
    public void setUpTestName(Method method, Object[] testData) {
        /* If a Test Case has NO Data Provider then skip below testName.set method */
        if (testData.length != 0) {
            TEST_NAME.set(method.getName() + "_" + testData[0]);
        } else {
            TEST_NAME.set(method.getName());
        }

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Test name getter
     *
     * @return returns name of test
     */
    @Override
    public String getTestName() {
        return TEST_NAME.get();
    }



    /**
     * Clears test name
     */
    @AfterMethod(alwaysRun = true)
    public void clearTestName() {
        TEST_NAME = new ThreadLocal<>();
    }

    public RequestSpecification getApplicationJSONSpecification() {

        return given()
            .auth().preemptive().basic(EMAIL, JIRA_API_TOKEN)
            .contentType("application/json")
            .when();
    }

    private String getLatestIssueKeyByType(String issueType) {

        baseURI = format("%s%s", BASE_URL, SEARCH_ENDPOINT);

        String jql = format("project = %s AND type = %s ORDER BY created DESC", PROJECT_KEY, issueType);

        Response response = getApplicationJSONSpecification()
            .queryParam("jql", jql)
            .queryParam("maxResults", 1)
            .get();

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SC_OK, "Incorrect status code. Expected 200.");
        return response.getBody().jsonPath().get("issues.key[0]");
    }
}