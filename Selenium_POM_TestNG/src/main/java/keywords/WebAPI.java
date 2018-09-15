package keywords;

import java.io.File;
import java.io.IOException;
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
import helpers.FileHelper;

public class WebAPI {
    static String jsonResponseContent;
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
            jsonResponseContent = EntityUtils.toString(httpResponse.getEntity());
            Utility.logInfo("WebAPI", "Response [DELETE]: \n" + jsonResponseContent, 1);
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
            jsonResponseContent = EntityUtils.toString(httpResponse.getEntity());
            Utility.logInfo("WebAPI", "Response [POST]: \n" + jsonResponseContent, 1);
            EntityUtils.consumeQuietly(httpResponse.getEntity());
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
            jsonResponseContent = EntityUtils.toString(httpResponse.getEntity());
            Utility.logInfo("WebAPI", "Response [PUT]: \n" + jsonResponseContent, 1);
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
            jsonResponseContent = EntityUtils.toString(httpResponse.getEntity());
            Utility.logInfo("WebAPI", "Response [GET]: \n" + jsonResponseContent, 1);
            request.releaseConnection();
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseNode(String jsonNode) {
        String nodeValue = FileHelper.getJSONNode(jsonResponseContent, jsonNode);
        Utility.logInfo("WebAPI", "getResponseNode :: [" + jsonNode + "] : " + nodeValue, 0);
        return nodeValue;
    }

    public static JSONObject convertResponseToJSONObject() {
        return convertStringToJSONObject(jsonResponseContent);
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

    public static void verifyResponseNode(String jsonNode, String expectedValue, Enums.OPERATOR expectedOperator) {
        String currentValue = getResponseNode(jsonNode);
        Utility.verifyValues("verifyResponseNode", currentValue, expectedValue, expectedOperator);
    }

    public static void verifyErrorMessage(HttpResponse httpResponse, String expectedStatusCode, String expectedMessage, Enums.OPERATOR expectedOperator) {
        //verify Status Code
        String statusCode = String.valueOf(httpResponse.getStatusLine().getStatusCode());
        Utility.verifyValues("verifyErrorMessage :: statusCode", statusCode, expectedStatusCode, Enums.OPERATOR.equal);

        //verify Error message
        verifyResponseNode("error.message", expectedMessage, expectedOperator);
    }

    public static void verifyResponseContent(HttpResponse httpResponse, String expectedStatusCode, String expectedResponseJson) {
        //verify Status Code
        String statusCode = String.valueOf(httpResponse.getStatusLine().getStatusCode());
        Utility.verifyValues("verifyResponseContent :: statusCode", statusCode, expectedStatusCode, Enums.OPERATOR.equal);

        //verify Response Json
        Utility.verifyValues("verifyResponseContent", jsonResponseContent, expectedResponseJson, Enums.OPERATOR.equal);
    }
}
