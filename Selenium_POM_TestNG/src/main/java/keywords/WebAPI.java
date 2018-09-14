package keywords;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
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

    public static HttpResponse delete(String url, String contentType, String authorization) {
        try {
            Utility.logInfo("WebAPI", "Request [DELETE]: " + url, 1);
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
            Utility.logInfo("WebAPI", "Response [DELETE]: \n" + convertResponseToString(httpResponse), 1);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse post(String url, String contentType, String authorization, String body) {
        try {
            Utility.logInfo("WebAPI", "Request [POST]: " + url, 1);
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
                Utility.logInfo("WebAPI", "Body [POST]: " + body, 1);
                StringEntity params = new StringEntity(body);
                request.setEntity(params);
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            Utility.logInfo("WebAPI", "Response [POST]: \n" + convertResponseToString(httpResponse), 1);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse put(String url, String contentType, String authorization, String body) {
        try {
            Utility.logInfo("WebAPI", "Request [PUT]: " + url, 1);
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
                Utility.logInfo("WebAPI", "Body [PUT]: " + body, 1);
                StringEntity params = new StringEntity(body);
                request.setEntity(params);
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            Utility.logInfo("WebAPI", "Response [PUT]: \n" + convertResponseToString(httpResponse), 1);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse get(String url, String contentType, String authorization) {
        try {
            Utility.logInfo("WebAPI", "Request [GET]: " + url, 1);
            HttpGet request = new HttpGet(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","Bearer " + authorization);
                request.addHeader("Content-Type","application/json");
            } else if (contentType == "x-www-form-urlencoded") {
                if(authorization != null) request.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes()) + ":");
                request.addHeader("Content-Type","application/x-www-form-urlencoded");
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            Utility.logInfo("WebAPI", "Response [GET]: \n" + EntityUtils.toString(httpResponse.getEntity()), 1);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertResponseToString(HttpResponse httpResponse) {
        String jsonContent = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line;
            //get content
            while ((line = reader.readLine()) != null) {
                System.out.println("ZZZ: " + line);
                jsonContent = jsonContent + line + "\n";
            }
            return jsonContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseNode(HttpResponse httpResponse, String jsonNode, String delimiter) {
        String jsonContent = convertResponseToString(httpResponse);
        Utility.logInfo("API", "getResponseNode: " + jsonContent, 1);
        return FileHelper.getJSONNode(jsonContent, jsonNode, delimiter);
    }

    public static JSONObject convertResponseToJSONObject(HttpResponse httpResponse) {
        String jsonContent = convertResponseToString(httpResponse);
        return convertStringToJSONObject(jsonContent);
    }

    public static JSONObject convertStringToJSONObject(String strJSONName) {
        try {
            String contentJSON = strJSONName;
            if (strJSONName.endsWith(".json")) {
                File file = new File(strJSONName);
                contentJSON = FileUtils.readFileToString(file, "utf-8");
            }
            JSONObject jsonObject = new JSONObject(contentJSON);
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
        String currentJson = convertResponseToString(httpResponse);
        Utility.verifyValues("verifyResponseContent", currentJson, expectedResponseJson, Enums.OPERATOR.equal);
    }

//    public static void main(String... args) throws IOException {
//        String accessToken = getAccessToken();

//        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/values/UI-Report-Chrome!G1:G10:append?valueInputOption=USER_ENTERED";
//        String body = "{\"range\": \"UI-Report-Chrome!G1:G10\",\"majorDimension\": \"COLUMNS\",\"values\": [[\"3/15/2018\"]]}";
//        HttpResponse httpResponse = post(url,"json", accessToken, body);

//        String url = "https://sheets.googleapis.com/v4/spreadsheets/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4:batchUpdate";
//        String body = "{\"requests\": [{\"insertDimension\": { \"range\": {\"sheetId\": 0,\"dimension\": \"COLUMNSAAAA\",\"startIndex\": 5,\"endIndex\": 6},\"inheritFromBefore\": false}},],}";
//        HttpResponse httpResponse = post(url,"json", accessToken, body);

//        verifyResponseContent(httpResponse, "400", Environments.DATA_PATH + "testData.json");

//        int statusCode = httpResponse.getStatusLine().getStatusCode();
//        Utility.logInfo("API", "Status Code: " +  statusCode, 1);
//        String abc = EntityUtils.toString(httpResponse.getEntity());
//        System.out.println(abc);
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
//    }
}
