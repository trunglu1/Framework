package keywords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import org.testng.Assert;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import utilities.Enums;
import utilities.Utility;
import constants.Environments;
import helpers.FileHelper;

public class WebAPI {

    public static HttpResponse sendRequest(Enums.METHOD_API method_api, String url, String contentType, String authorization, String body) {
        switch(method_api) {
            case PUT:
                return put(url, contentType, authorization, body);
            case DELETE:
                return delete(url, contentType, authorization);
            case POST:
                return post(url, contentType, authorization, body);
            default:
                return get(url, contentType, authorization);
        }
    }

    public static HttpResponse sendRequest(Enums.METHOD_API method_api, String url, String contentType, String authorization) {
        switch(method_api) {
            case PUT:
                return put(url, contentType, authorization, null);
            case DELETE:
                return delete(url, contentType, authorization);
            case POST:
                return post(url, contentType, authorization, null);
            default:
                return get(url, contentType, authorization);
        }
    }

    static HttpResponse delete(String url, String contentType, String authorization) {
        try {
            HttpDelete request = new HttpDelete(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
                request.setHeader("Accept", "application/json");
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

    static HttpResponse post(String url, String contentType, String authorization, String body) {
        try {
            HttpPost request = new HttpPost(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
                request.setHeader("Accept", "application/json");
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

    static HttpResponse put(String url, String contentType, String authorization, String body) {
        try {
            HttpPut request = new HttpPut(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
                request.setHeader("Accept", "application/json");
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

    static HttpResponse get(String url, String contentType, String authorization) {
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
            String contentJSon  = EntityUtils.toString(httpResponse.getEntity());
            Utility.logInfo("API", "getResponseNode: " +  contentJSon, 1);
            return FileHelper.getJSONNode(contentJSon, jsonNode, delimiter);
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

    public static JSONObject getResponseJson(HttpResponse httpResponse) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            String jsonContent = "";
            while ((line = reader.readLine()) != null) {
                jsonContent = jsonContent + line.trim();
            }
            System.out.println(jsonContent);
            JSONObject jsonObject = FileHelper.convertToJSONObject(jsonContent);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void verifyResponseNode(HttpResponse httpResponse, String jsonNode, String delimiter, String expectedValue, Enums.OPERATOR expectedOperator) {
        String currentValue = getResponseNode(httpResponse, jsonNode, delimiter);
        Utility.verifyValues("verifyResponseNode :: ", currentValue, expectedValue, expectedOperator);
    }

    public static void verifyErrorMessage(HttpResponse httpResponse, String expectedStatusCode, String expectedMessage, Enums.OPERATOR expectedOperator) {
        //verify Status Code
        String statusCode = String.valueOf(httpResponse.getStatusLine().getStatusCode());
        Utility.verifyValues("verifyErrorMessage :: statusCode", statusCode, expectedStatusCode, Enums.OPERATOR.equal);

        //verify Error message
        verifyResponseNode(httpResponse, "error>message", ">", expectedMessage, expectedOperator);
    }

    public static void verifyResponseContent(HttpResponse httpResponse, String expectedStatusCode, String expectedResponseJson) {
        //verify Status Code
        String statusCode = String.valueOf(httpResponse.getStatusLine().getStatusCode());
        Utility.verifyValues("verifyResponseContent :: statusCode", statusCode, expectedStatusCode, Enums.OPERATOR.equal);

        //verify Response Json
        JSONObject currentJson = getResponseJson(httpResponse);
        JSONObject expectedJson = FileHelper.convertToJSONObject(expectedResponseJson);
        Assert.assertEquals(currentJson.toString(), expectedJson.toString());
    }

    public static void main(String... args) throws IOException {
        String accessToken = getAccessToken();

        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/values/UI-Report-Chrome!G1:G10:append?valueInputOption=USER_ENTERED";
        String body = "{\"range\": \"UI-Report-Chrome!G1:G10\",\"majorDimension\": \"COLUMNS\",\"values\": [[\"3/15/2018\"]]}";
        HttpResponse httpResponse = post(url,"json", accessToken, body);

//        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4:batchUpdate";
//        String body = "{\"requests\": [{\"insertDimension\": { \"range\": {\"sheetId\": 0,\"dimension\": \"COLUMNSAAAA\",\"startIndex\": 5,\"endIndex\": 6},\"inheritFromBefore\": false}},],}";
//        HttpResponse httpResponse = post(url,"json", accessToken, body);

//        verifyResponseContent(httpResponse, "400", Environments.DATA_PATH + "testData.json");

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Utility.logInfo("API", "Status Code: " +  statusCode, 1);
        String abc = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(abc);
//        System.out.println(getResponseJson(httpResponse).toString());
//        JSONObject expectedJson1 = getResponseJson(httpResponse);
//        System.out.println(FileHelper.convertToJSONObject(Environments.DATA_PATH + "testData.json").toString());

//        verifyResponseContent(httpResponse,"400", Environments.DATA_PATH + "testData.json");
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            System.out.println(line);
//        }

//        String childObject = FileHelper.getJSONNode("D:\\Training\\test\\Java-BDD-Automation\\Selenium_POM_TestNG\\src\\main\\resources\\credentials.json", "installed>redirect_uris", ">");
//        System.out.println(childObject);
    }
}
