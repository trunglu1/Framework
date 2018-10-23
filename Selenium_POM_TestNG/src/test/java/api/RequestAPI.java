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
    static String jsonFile = Environments.CREDENTIALS_FILE;
    static String url_Server = "https://sheets.googleapis.com/v4/spreadsheets/" +
            FileHelper.getJSONNode(jsonFile,"installed.spreadsheet_id");

    public static String getAccessToken() {
        String url = "https://www.googleapis.com/oauth2/v4/token";
        String client_id = FileHelper.getJSONNode(jsonFile,"installed.client_id");
        String client_secret = FileHelper.getJSONNode(jsonFile,"installed.client_secret");
        String refresh_token = FileHelper.getJSONNode(jsonFile,"installed.refresh_token");
//        String client_id = "403830840902-h9jerviucakgure60tj1s6i7id7vo2a6.apps.googleusercontent.com";
//        String client_secret = "pgypiR4TL1pAPdeJ-6DWrptV";
//        String refresh_token = "1/0oK7pq98zmn7a9QsYtNgn8uGvHhRJMwa2lqetHssBrE";
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

    public static void main(String[] args) throws InterruptedException {
        String abc = getAccessToken();
        System.out.print(abc);
    }
}
