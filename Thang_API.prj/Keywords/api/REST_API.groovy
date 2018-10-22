package api

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.*
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.*
import org.apache.http.util.EntityUtils

import groovy.json.JsonSlurper
import internal.GlobalVariable

public class REST_API {
	private static def httpResponseValue =  [:]

	/******************************************************
	 * send a httpClient request
	 * Author: thangctran
	 * @param url : The url
	 * @param method : The method POST; GET; PUT; DELETE
	 * @param paramsBody : The request body 
	 * @param authorization : The authorization key
	 * @return The httpResponse
	 */
	@Keyword
	def sendRequest(String url, String method, String contentType, String paramsBody, String authorization=null){
		//Initialize HttpClient
		HttpClient httpClient = new DefaultHttpClient()
		def request = method
		if(method == "POST") request = new HttpPost(url);
		else if (method == "GET") request = new HttpGet(url);
		else if (method == "PUT") request = new HttpPut(url);
		else if (method == "DELETE") request = new HttpDelete(url)
		//Input header request
		if(contentType == "json"){
			request.addHeader("Content-Type","application/json")
			if(authorization != null) request.addHeader("Authorization", authorization)
			else request.addHeader("Authorization", GlobalVariable.accessToken)
		} else if (contentType == "x-www-form-urlencoded") {
			request.addHeader("Content-Type","application/x-www-form-urlencoded")
		}
		try {
			//Input request body
			if(paramsBody != null){
				StringEntity input = new StringEntity(paramsBody);
				request.setEntity(input);
			}
			//Send request
			HttpResponse response = httpClient.execute(request)
			//Get the response
			HttpEntity entity = response.getEntity()
			JsonSlurper jsonSlurper = new JsonSlurper()
			httpResponseValue = jsonSlurper.parseText(EntityUtils.toString(entity, "UTF-8"))
			WebUI.comment('***Request [' + method + ']: ' + url + '\n***Response: ' + httpResponseValue.toString())
			return response
		} catch (IOException e) {
			e.printStackTrace()
			return null
		} finally {
			request.releaseConnection()
		}
	}

	/******************************************************
	 * get GoogleSheet access token key
	 * Author: thangctran
	 * @return None
	 */
	@Keyword
	def String getGoogleSheetToken(){
		String url = 'https://www.googleapis.com/oauth2/v4/token'
		String client_id = "403830840902-h9jerviucakgure60tj1s6i7id7vo2a6.apps.googleusercontent.com"
		String client_secret = 'pgypiR4TL1pAPdeJ-6DWrptV'
		String refresh_token = '1/0oK7pq98zmn7a9QsYtNgn8uGvHhRJMwa2lqetHssBrE'
		String bobyRequest = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token", client_id, client_secret, refresh_token)
		WebUI.comment('***bobyRequest: ' + bobyRequest)
		def response = sendRequest('https://www.googleapis.com/oauth2/v4/token', 'POST', 'x-www-form-urlencoded', bobyRequest)
//		verifyResponseStatusCode(response, 200)
//		WebUI.comment('***access_token: ' + httpResponseValue.access_token)
//		String access_token = httpResponseValue.access_token
		GlobalVariable.googleAccessToken = "Bearer " + httpResponseValue.access_token
//		return access_token;
	}

	/******************************************************
	 * verify the response Status Code
	 * Author: thangctran
	 * @param response : The http response
	 * @param statusCode : The expected status code
	 * @return none
	 */
	@Keyword
	def verifyResponseStatusCode(def response, Integer statusCode){
		WebUI.verifyEqual(statusCode, response.getStatusLine().getStatusCode())
	}
}
