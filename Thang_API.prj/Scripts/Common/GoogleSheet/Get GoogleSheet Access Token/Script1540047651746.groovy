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

//def jsonSlurper = new JsonSlurper()
//
//def getAccessToken = WS.sendRequest(findTestObject('GoogleSheet/Get GoogleSheet Access Token'))
//
//def json_AccessToken = jsonSlurper.parseText(getAccessToken.getResponseText())
//
//WebUI.comment(json_AccessToken.toString())
//
//println(json_AccessToken.error)
CustomKeywords.'api.REST_API.getGoogleSheetToken'()

WebUI.comment(GlobalVariable.googleAccessToken)

WS.sendRequest(findTestObject('GoogleSheet/Set Value Range',
	 [('sheetName') : 'UI-Report-Firefox',
		 ('range') : 'C3',
		 ('valueRange') : 'Hello',
		 ('authorization') : GlobalVariable.googleAccessToken]))

