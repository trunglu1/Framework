package tests.api;


import org.apache.http.HttpResponse;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import api.RequestAPI;
import keywords.WebAPI;
import google.GoogleSheets;
import utilities.Enums;
import utilities.Utility;

public class ValueRangeTest {
    @BeforeSuite
    public void startTestSuite(){
        GoogleSheets.googleSheetInfo.put("sheetName", "API-Report");
        GoogleSheets.googleSheetInfo.put("sheetId", "945538131");
    }

//    @AfterSuite
//    public void endTestSuite(){
//
//    }

    @Test(priority = 0, description = "API001-SetValue- Set value range on google sheet")
    public void API001_SetValue() {
        String sheetName = "UI-Report-Safari";
        String rangeRow = "A2:C2";
        String valueRangeRow = "\"message\",\"Invalid\",\"status\"";

        Utility.logInfo("STEP", "Get accessToken key", 1);
        RequestAPI.accessToken = RequestAPI.getAccessToken();

        Utility.logInfo("STEP", "Set value range Row :" + rangeRow, 1);
        RequestAPI.requestSetValueRange(sheetName, rangeRow, valueRangeRow, "ROWS");

        Utility.logInfo("STEP", "Verify value range Row updated to google sheet successful", 1);
        RequestAPI.requestGetValueRange(sheetName, rangeRow);
        WebAPI.verifyResponseNode("values", "[[" + valueRangeRow + "]]", Enums.OPERATOR.equal);

        Utility.logInfo("STEP", "Clear range value", 1);
        RequestAPI.requestClearValueRange(sheetName, rangeRow);
    }

    @Test(priority = 0, description = "API002-SetValue- Set invalid value range on google sheet")
    public void API002_SetValue() {
        String sheetName = "UI-Report-Safari";
        String invalidSheetName = "UI-Report-Invalid";
        String rangeRow = "A2:C2";
        String valueRangeRow = "1234,6789,567.89";
        String errorSheetNameMessage = "Unable to parse range: " + invalidSheetName + "!" + rangeRow;
        String errorDimensionMessage = "Invalid value at 'data.major_dimension' (TYPE_ENUM), \"INVALID_ROWS\"";

        Utility.logInfo("STEP", "Get accessToken key", 1);
        RequestAPI.accessToken = RequestAPI.getAccessToken();

        Utility.logInfo("STEP", "Set value range Row :" + rangeRow, 1);
        HttpResponse httpResponse = RequestAPI.requestSetValueRange(invalidSheetName, rangeRow, valueRangeRow, "ROWS");

        Utility.logInfo("STEP", "Verify StatusCode and Sheet error Message", 1);
        WebAPI.verifyErrorMessage(httpResponse, "400", errorSheetNameMessage, Enums.OPERATOR.equal);

        Utility.logInfo("STEP", "Set value range Row :" + rangeRow, 1);
        httpResponse = RequestAPI.requestSetValueRange(sheetName, rangeRow, valueRangeRow, "INVALID_ROWS");

        Utility.logInfo("STEP", "Verify StatusCode and Sheet error Message", 1);
        WebAPI.verifyErrorMessage(httpResponse, "400", errorDimensionMessage, Enums.OPERATOR.equal);
    }
}
