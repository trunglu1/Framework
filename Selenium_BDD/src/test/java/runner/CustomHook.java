package runner;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import constants.Environments;
import google.GoogleSheets;
import helpers.FileHelper;
import keywords.WebUI;
import utilities.Utility;

public class CustomHook {
    static String startDate;
    static long startTime;
    public static String scenarioName;

    @Before
    public static void beforeScenario(Scenario scenario) {
        startDate = Utility.getUnique("yyyy/MM/dd HH:mm:ss");
        startTime = System.currentTimeMillis();
        scenarioName = scenario.getName();
        Utility.logInfo("TESTCASE","*** Execute TestCase: " + scenarioName + " ***", 1);
    }

    @After
    public static void afterScenario(Scenario scenario) {
        if(scenario.getStatus() != "passed"){
            String captureImage = FileHelper.getXmlNodeValue("//Configuration/CaptureImage/text()",0);
            if(captureImage.toLowerCase() != "false") {
                WebUI.captureScreen(Environments.REPORTS_PATH + "images\\" + scenario.getName().split("-")[0] + "_" + Utility.getUnique("yyMMdd_HHmmss") + ".png");
            }
        }

        WebUI.deleteAllCookies();
        Utility.logInfo("TESTCASE","*** End TestCase: " + scenario.getName() + " ***", 1);

        //Update to google sheet: https://docs.google.com/spreadsheets/d/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/edit#gid=0
        long millis = System.currentTimeMillis() - startTime;

        // convert duration format ("HH:mm:ss.SSS")
        String duration = String.format("%02d:%02d:%02d.%03d", millis / 3600000, ((millis/1000) % 3600) / 60, ((millis/1000) % 60), (millis % 1000));
        GoogleSheets.updateTestCaseStatus(scenario.getName(), startDate, duration, scenario.getStatus());
        Utility.testData.clear();
    }
}
