import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper

def jsonSlurper = new JsonSlurper()

def getAccessToken = WS.sendRequest(findTestObject('GoogleSheet/Get GoogleSheet Access Token'))

def json_AccessToken = jsonSlurper.parseText(getAccessToken.getResponseText())

WebUI.comment(json_AccessToken.toString())
//
//println(json_AccessToken.error)
//CustomKeywords.'api.REST_API.getGoogleSheetToken'()
//
//WebUI.comment(GlobalVariable.sheetName)
//
//WS.sendRequest(findTestObject('GoogleSheet/Set result value', [('sheetName') : 'UI-Report-Firefox', ('range') : 'C3', ('valueRange') : 'Hello'
//            , ('authorization') : GlobalVariable.sheetName]))

//response_GoogleAccessToken = WS.sendRequest(findTestObject('GoogleSheet/Get GoogleSheet Access Token'))
//def jsonSlurper = new JsonSlurper()
//def json_AccessToken = jsonSlurper.parseText(response_GoogleAccessToken.getResponseText())
//WebUI.comment(json_AccessToken.toString())
//GlobalVariable.sheetName = json_AccessToken.access_token
//WebUI.comment(GlobalVariable.sheetName)
//abc = WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Insert daily result column',
//	['sheetId': '198180008',
//		,'authorization': GlobalVariable.sheetName]))


//WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Set result value',
//	['sheetName': 'UI-Report-Firefox'
//		,'range': 'C4'
//		, 'valueRange': ''
//		,'authorization': GlobalVariable.sheetName]))

abc = WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Get list test cases',
	['sheetName': 'UI-Report-Chrome'
		,'authorization': GlobalVariable.sheetName]))

def json_abc = jsonSlurper.parseText(abc.getResponseText())
//WebUI.comment(json_abc.values.size().toString())
_listTC = []
for(_TCName in json_abc.values){
	WebUI.comment(_TCName[0].toString())
	_listTC.add(_TCName[0].toString())
}
WebUI.comment(_listTC.toString())
findRowIndex1 = _listTC.indexOf('FE001-Login - Login successful')
findRowIndex2 = _listTC.indexOf('BE001-Login - Login to page successful')
findRowIndex3 = _listTC.indexOf('2222')
WebUI.comment('Find Row: ' + findRowIndex1 + "---" + findRowIndex2 + "---" + findRowIndex3)
//WS.verifyResponseStatusCode(abc, 200)