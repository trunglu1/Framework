package tests.api;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import api.RequestAPI;
import utilities.Utility;

public class ValueRangeTest {
    @BeforeSuite
    public void startTestSuite(){

    }

    @AfterSuite
    public void endTestSuite(){

    }

    @Test(priority = 0, description = "API001-SetValue- Set value range on google sheet")
    public void API001_SetValue() {
        String sheetName = "UI-Report-Safari";
        String range = "A1:C1";
        String valueRange = "\"message\",\"Invalid\",\"status\"";

        //get accessToken key
        Utility.logInfo("STEP", "Get accessToken key", 1);
        RequestAPI.accessToken = RequestAPI.getAccessToken();

        Utility.logInfo("STEP", "Set value range", 1);
        RequestAPI.setValueRange(sheetName, range, valueRange, "ROWS");

        Utility.logInfo("STEP", "Verify value range updated to google sheet successful", 1);

    }
}
