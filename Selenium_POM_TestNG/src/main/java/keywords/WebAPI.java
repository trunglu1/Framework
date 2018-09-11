package keywords;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import utilities.Utility;

import java.io.FileReader;
import java.io.IOException;
import helpers.FileHelper;

public class WebAPI {
    public static void main(String... args) throws IOException {
//        String name = "API" + Utility.getUnique("HHmmss");
//        String tokenKey = ";";
//        String authorizationManager = "";
//        String contentType = "json";
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
        String jsonFile = "D:\\Self-Study\\Framework\\Selenium_POM_TestNG\\src\\main\\resources\\credentials.json";
        String token_uri = FileHelper.getJSONNode(jsonFile,"installed>token_uri", ">");
        System.out.println(token_uri);
//        String client_secret = FileHelper.getJSONNode(jsonFile,"installed>client_secret", ">");
//        String requestData = String.format("client_id=%s&client_secret=%s&refresh_token=${refresh_token}&grant_type=refresh_token", client_id, client_secret) ;
//        HttpUriRequest request = new HttpPost(url);
//        request.addHeader("Content-Type","application/x-www-form-urlencoded");


//        String childObject = FileHelper.getJSONNode("D:\\Training\\test\\Java-BDD-Automation\\Selenium_POM_TestNG\\src\\main\\resources\\credentials.json", "installed>redirect_uris", ">");
//        System.out.println(childObject);
    }
}
