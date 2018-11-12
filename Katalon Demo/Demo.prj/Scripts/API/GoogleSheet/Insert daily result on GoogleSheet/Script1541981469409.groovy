import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import groovy.json.JsonSlurper as JsonSlurper
import internal.GlobalVariable as GlobalVariable

'Variables'
def jsonSlurper = new JsonSlurper()
_startDate = CustomKeywords.'pdi.Utilities.getUnique'("yyyy/MM/dd HH:mm:ss")
_data = CustomKeywords.'pdi.Utilities.getDataRow'(findTestData('Data Files/API/GoogleSheet'), 1)
_paramsBody = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token"
	, _data['client_id'], _data['client_secret'], _data['refresh_token'])

'Access and get accessToken from GoogleSheet'
if(true){
	def response_GoogleAccessToken = WS.sendRequest(findTestObject('API/GoogleSheet/POST_Get GoogleSheet Access Token',
		['p_Body': _paramsBody]))
	_accessToken = jsonSlurper.parseText(response_GoogleAccessToken.getResponseText()).access_token
	WS.comment(_accessToken)
}

'Insert daily result to GoogleSheet'
if(true){
	WS.sendRequest(findTestObject('API/GoogleSheet/POST_Insert daily result column',
		['p_Spreadsheet_Id': _data['spreadsheet_id']
		,'p_SheetId': _data[GlobalVariable.SheetName]
		,'p_Authorization': 'Bearer ' + _accessToken]))
}

'Fill Start time daily'
WS.sendRequest(findTestObject('API/GoogleSheet/PUT_Set result value',
	['p_Spreadsheet_Id': _data['spreadsheet_id'], 'p_SheetName': GlobalVariable.SheetName
	,'p_Range': 'F1', 'p_ValueRange': _startDate, 'p_Authorization': 'Bearer ' + _accessToken]))