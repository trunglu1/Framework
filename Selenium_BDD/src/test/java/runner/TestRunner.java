package runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import constants.Environments;
import drivers.Driver;
import keywords.WebUI;
import google.GoogleSheets;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"steps", "runner"},
        plugin = { "pretty", "html:target/cucumber-html-reports"}
)

public class TestRunner {
    @BeforeClass
    public static void beforeSuite() {
        Driver.setDriver(Driver.setSeleniumDrivers());
        if (Environments.INSERT_NEW_RESULT && Environments.REPORT_GOOGLE_SHEET) {
            GoogleSheets.insertColumnTestStatus();
            Environments.INSERT_NEW_RESULT = false;
        }
    }

    @AfterClass
    public static void afterSuite() {
        WebUI.closeDriver();
    }

}
