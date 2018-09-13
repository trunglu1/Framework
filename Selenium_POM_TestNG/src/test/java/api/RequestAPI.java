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
    static String jsonFile = Environments.RESOURCES_PATH + "credentials.json";
    static String url_Server = "https://sheets.googleapis.com/v4/spreadsheets/" +
            FileHelper.getJSONNode(jsonFile,"installed>spreadsheet_id", ">");
    public static String getAccessToken() {
        String url = "https://www.googleapis.com/oauth2/v4/token";
        String client_id = FileHelper.getJSONNode(jsonFile,"installed>client_id", ">");
        String client_secret = FileHelper.getJSONNode(jsonFile,"installed>client_secret", ">");
        String refresh_token = FileHelper.getJSONNode(jsonFile,"installed>refresh_token", ">");
        String bobyRequest = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token", client_id, client_secret, refresh_token) ;
        // post request
        HttpResponse httpResponse = WebAPI.sendRequest(Enums.METHOD_API.POST, url,"x-www-form-urlencoded", null, bobyRequest);
        return WebAPI.getResponseNode(httpResponse, "access_token", ">");
    }

    public static HttpResponse setCellValue(String accessToken, String sheetName, String columnName, String rowIndex, String text) {
        String url = url_Server + "/values/" + sheetName + "!" + columnName + rowIndex + "?valueInputOption=RAW";
        String bobyJSON = Body.setCellValue(sheetName, columnName, rowIndex, text);
        return WebAPI.sendRequest(Enums.METHOD_API.PUT, url, "json", accessToken, bobyJSON);
    }

    public static HttpResponse getCellValue(String accessToken, String sheetName, String columnName, String rowIndex) {
        String url = url_Server + "/values/" + sheetName + "!" + columnName + rowIndex;
        return WebAPI.sendRequest(Enums.METHOD_API.GET, url,"json", accessToken);
    }

    public static HttpResponse clearCellValue(String accessToken, String sheetName, String columnName, String rowIndex) {
        String url = url_Server + "/values/" + sheetName + "!" + columnName + rowIndex + ":clear";
        return WebAPI.sendRequest(Enums.METHOD_API.POST, url, "json", accessToken, "{}");
    }
    public static void main(String... args) throws IOException {
        String accessToken = getAccessToken();
//        HttpResponse httpResponse = setCellValue(accessToken,"UI-Report-Chrome", "E", "5", "Hello");
//        HttpResponse httpResponse = getCellValue(accessToken,"UI-Report-Chrome", "F", "1");
        HttpResponse httpResponse = clearCellValue(accessToken,"UI-Report-Chrome", "E", "4");
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Utility.logInfo("API", "Status Code: " +  statusCode, 1);
        String abc = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(abc);
    }
}
