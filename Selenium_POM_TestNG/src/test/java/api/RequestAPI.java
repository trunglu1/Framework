package api;

import java.io.IOException;
import constants.Environments;
import helpers.FileHelper;
import keywords.WebAPI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import utilities.Enums;
import utilities.Utility;

public class RequestAPI {
    public static String accessToken = null;
    static String jsonFile = Environments.RESOURCES_PATH + "credentials.json";
    static String url_Server = "https://sheets.googleapis.com/v4/spreadsheets/" +
            FileHelper.getJSONNode(jsonFile,"installed.spreadsheet_id");

    public static String getAccessToken() {
        String url = "https://www.googleapis.com/oauth2/v4/token";
        String client_id = FileHelper.getJSONNode(jsonFile,"installed.client_id");
        String client_secret = FileHelper.getJSONNode(jsonFile,"installed.client_secret");
        String refresh_token = FileHelper.getJSONNode(jsonFile,"installed.refresh_token");
        String bobyRequest = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token", client_id, client_secret, refresh_token) ;
        // post request
        HttpResponse httpResponse = WebAPI.post(url,"x-www-form-urlencoded", null, bobyRequest);
        return WebAPI.getResponseNode("access_token");
    }

    public static HttpResponse requestSetValueRange(String sheetName, String range, String valueRange, String dimension) { //dimension: ROWS or COLUMNS
        String url = url_Server + "/values/" + sheetName + "!" + range + "?valueInputOption=RAW";
        String bodyJSON = Body.setCellValue(sheetName, range, valueRange, dimension);
        return WebAPI.put(url, "json", accessToken, bodyJSON);
    }

    public static HttpResponse requestGetValueRange(String sheetName, String range) {
        String url = url_Server + "/values/" + sheetName + "!" + range;
        return WebAPI.get(url,"json", accessToken);
    }

    public static HttpResponse requestClearValueRange(String sheetName,  String range) {
        String url = url_Server + "/values/" + sheetName + "!" + range + ":clear";
        return WebAPI.post(url, "json", accessToken, "{}");
    }

    public static void main(String... args) throws IOException {
//        String accessToken = getAccessToken();
//        HttpResponse httpResponse = setValueRange(accessToken,"UI-Report-Chrome", "E5", "Hello");
//        HttpResponse httpResponse = getValueRange(accessToken,"UI-Report-Chrome", "F1");
//        HttpResponse httpResponse = clearValueRange("UI-Report-Chrome", "E4");
//        int statusCode = httpResponse.getStatusLine().getStatusCode();

        accessToken = getAccessToken();
        String sheetName = "UI-Report-Safari";
        String range1 = "A3:C3";
        String valueRange1 =  "\"123\",\"234.78\",\"42343\"";
        String range2 = "A4:A6";
        String valueRange2 = "\"message\",\"Invalid\",\"status\"";
//        HttpResponse httpResponse1 = getValueRange("UI-Report-Safari", range1);
        requestSetValueRange(sheetName, range1, valueRange1, "Invalid_ROWS");
//        HttpResponse httpResponse = setValueRange(sheetName, range2, valueRange2, "COLUMNS");
//        requestGetValueRange(sheetName, range1);
//        WebAPI.verifyResponseNode("values", "[[" + valueRange1 + "]]", Enums.OPERATOR.equal);
//        HttpResponse httpResponse2 = getValueRange(sheetName, range1);
//        System.out.println(WebAPI.getResponseNode("values"));
//        System.out.println(WebAPI.getResponseNode("range"));
    }
}
