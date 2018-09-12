package keywords;

import java.io.*;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import utilities.Enums;
import utilities.Utility;
import constants.Environments;
import helpers.FileHelper;

public class WebAPI {

    public static HttpResponse post(String url, String contentType, String authorization, String body) {
        try {
            HttpPost request = new HttpPost(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
            } else if (contentType == "x-www-form-urlencoded") {
                if(authorization != null) request.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes()) + ":");
                request.addHeader("Content-Type","application/x-www-form-urlencoded");
            }
            if(body != null) {
                StringEntity params = new StringEntity(body);
                request.setEntity(params);
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse put(String url, String contentType, String authorization, String body) {
        try {
            HttpPut request = new HttpPut(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
            } else if (contentType == "x-www-form-urlencoded") {
                if(authorization != null) request.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes()) + ":");
                request.addHeader("Content-Type","application/x-www-form-urlencoded");
            }
            if(body != null) {
                StringEntity params = new StringEntity(body);
                request.setEntity(params);
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse get(String url, String contentType, String authorization) {
        try {
            HttpGet request = new HttpGet(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
            } else if (contentType == "x-www-form-urlencoded") {
                if(authorization != null) request.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes()) + ":");
                request.addHeader("Content-Type","application/x-www-form-urlencoded");
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseNode(HttpResponse httpResponse, String jsonNode, String delimiter) {
        try {
            String containJSon  = EntityUtils.toString(httpResponse.getEntity());
            Utility.logInfo("API", "Contain: " +  containJSon, 1);
            return FileHelper.getJSONNode(containJSon, jsonNode, delimiter);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAccessToken() {
        String url = "https://www.googleapis.com/oauth2/v4/token";
        String jsonFile = Environments.RESOURCES_PATH + "credentials.json";
        String client_id = FileHelper.getJSONNode(jsonFile,"installed>client_id", ">");
        String client_secret = FileHelper.getJSONNode(jsonFile,"installed>client_secret", ">");
        String refresh_token = "1/P1lKpb4AGKSI7jkPlgjQj8KBgUmlmk5ki6ibk9wnU0g";
        String requestData = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token", client_id, client_secret, refresh_token) ;
        // post request
        HttpResponse httpResponse = post(url,"x-www-form-urlencoded", null, requestData);
        return getResponseNode(httpResponse, "access_token", ">");
    }

    public static void verifyResponseNode(HttpResponse httpResponse, String jsonNode, String delimiter, String expectedValue, Enums.OPERATOR expectedOperator) {
        String currentValue = getResponseNode(httpResponse, jsonNode, delimiter);
        Utility.verifyValues("verifyResponseNode :: ", currentValue, expectedValue, expectedOperator);
    }



    public static void main(String... args) throws IOException {
        String accessToken = getAccessToken();

        String jsonFile = Environments.RESOURCES_PATH + "credentials.json";
        String spreadsheetId = FileHelper.getJSONNode(jsonFile,"installed>spreadsheet_id", ">");

//        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/values/UI-Report-Chrome!G:G?valueInputOption=RAW";
//        String body = "{\"range\": \"UI-Report-Chrome!G:G\",\"values\": [[\"kkk\"]],\"majorDimension\": \"ROWS\"}";
//        HttpResponse httpResponse = put(url,"json", accessToken, body);


//        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/values/UI-Report-Chrome!A1:D1";
//        HttpResponse httpResponse = get(url,"json", accessToken);

//        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/values/UI-Report-Chrome!G1:clear";
//        HttpResponse httpResponse = post(url,"json", accessToken, "{}");

//
        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/values/UI-Report-Chrome!G1:G10:append?valueInputOption=USER_ENTERED";
        String body = "{\"range\": \"UI-Report-Chrome!G1:G10\",\"majorDimension\": \"COLUMNS\",\"values\": [[\"3/15/2018\"]]}";
        HttpResponse httpResponse = post(url,"json", accessToken, body);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Utility.logInfo("API", "Status Code: " +  statusCode, 1);
        String abc = EntityUtils.toString(httpResponse.getEntity());

        System.out.println(abc);
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            System.out.println(line);
//        }

//        String childObject = FileHelper.getJSONNode("D:\\Training\\test\\Java-BDD-Automation\\Selenium_POM_TestNG\\src\\main\\resources\\credentials.json", "installed>redirect_uris", ">");
//        System.out.println(childObject);
    }
}
