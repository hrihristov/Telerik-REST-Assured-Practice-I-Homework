package dataproviders;

import org.testng.annotations.DataProvider;

import static Utils.Constants.BUG_DESCRIPTION;
import static Utils.Constants.BUG_NAME;
import static Utils.Constants.BUG_SUMMARY;
import static Utils.Constants.PROJECT_KEY;
import static Utils.Constants.STORY_MULTILINE_DESCRIPTION;
import static Utils.Constants.STORY_NAME;
import static Utils.Constants.STORY_SUMMARY;

public class IssueFields {

    @DataProvider
    public static Object[][] fieldsPerIssue() {
        Object[][] dataset = new Object[2][5];

        dataset[0][0] = "Create a story successfully";
        dataset[0][1] = PROJECT_KEY;
        dataset[0][2] = STORY_SUMMARY;
        dataset[0][3] = STORY_MULTILINE_DESCRIPTION;
        dataset[0][4] = STORY_NAME;

        dataset[1][0] = "Create a bug successfully";
        dataset[1][1] = PROJECT_KEY;
        dataset[1][2] = BUG_SUMMARY;
        dataset[1][3] = BUG_DESCRIPTION;
        dataset[1][4] = BUG_NAME;

        return dataset;
    }
}
