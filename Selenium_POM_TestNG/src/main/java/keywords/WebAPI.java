package keywords;


import constants.Environments;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import utilities.Utility;

import java.io.*;
import java.util.Base64;

import helpers.FileHelper;

public class WebAPI {
    public static HttpResponse post(String url, String contentType, String authorization, String body) {
        try {
            HttpPost request = new HttpPost(url);
            if(contentType.toLowerCase() == "json"){
                if(authorization != null) request.addHeader("Authorization","bearer" + authorization);
                request.addHeader("Content-Type","application/json");
            } else if (contentType == "x-www-form-urlencoded") {
                if(authorization != null) request.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes()) + ":");
                request.addHeader("Content-Type","application/x-www-form-urlencoded");
            }
            if(body != null) {
                StringEntity params = new StringEntity(body);
                request.setEntity(params);
            }
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String... args) throws IOException {
        String name = "API" + Utility.getUnique("HHmmss");
        String tokenKey = ";";
        String authorizationManager = "";
        String contentType = "json";
//        String url ="https://0.docs.google.com/spreadsheets/u/1/d/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/bind?id=1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4&sid=4df6b3db668b155c&token=AC4w5VjgSOFzNcy6PHclFmhvklfgborhGA%3A1536636967284&includes_info_params=true&VER=8&lsq=1536636966387&u=08556518214067420109&c=1&w=1&gsi=1&ssfi=1215&smv=47&cimpl=1&RID=rpc&SID=E25322DE7F2C861D&CI=1&AID=87&TYPE=xmlhttp&zx=7hosw6ipg1lt&t=1";
//        HttpUriRequest request = new HttpGet(url);
//        //Input header request
//        if(contentType == "json"){
//            request.addHeader("Authorization","bearer" + tokenKey);
//            request.addHeader("Content-Type","application/json");
//        } else if (contentType == "x-www-form-urlencoded") {
//            request.addHeader("Authorization", authorizationManager);
//            request.addHeader("Content-Type","application/x-www-form-urlencoded");
//        }
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
//        int statusCode = httpResponse.getStatusLine().getStatusCode();
//        Utility.logInfo("API", "Status Code: " +  statusCode, 1);

        String url = "https://www.googleapis.com/oauth2/v4/token";
        String jsonFile = Environments.RESOURCES_PATH + "credentials.json";
        String client_id = FileHelper.getJSONNode(jsonFile,"installed>client_id", ">");
        System.out.println(client_id);
        String client_secret = FileHelper.getJSONNode(jsonFile,"installed>client_secret", ">");
        System.out.println(client_secret);
        String refresh_token = "1/P1lKpb4AGKSI7jkPlgjQj8KBgUmlmk5ki6ibk9wnU0g";
        String accessToken = "ya29.GlsVBpXnDLqoyEtIwhvYkZv4VKRUgJ9sAQlnHhgbUNHqYbgmAqnxmouxKrMVsJzX1A38Yi9okpLBSsEp3eDquEgeYGQ22pYZUBU-GGz-lhtYDz0ETViPCfHeCy4E";
        String requestData = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token", client_id, client_secret,refresh_token) ;
//        String requestData = String.format("{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"refresh_token\":\"%s\",\"grant_type\":\"refresh_token\"}", client_id, client_secret,refresh_token) ;
        Utility.logInfo("API", "requestData: " +  requestData, 1);
//        StringEntity params = new StringEntity(requestData);
//        HttpPost post = new HttpPost(url);
//        post.setHeader("Content-Type","application/x-www-form-urlencoded");
//        post.setEntity(params);
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( post );

        HttpResponse httpResponse = post(url,"x-www-form-urlencoded", null, requestData);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Utility.logInfo("API", "Status Code: " +  statusCode, 1);
//        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
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
