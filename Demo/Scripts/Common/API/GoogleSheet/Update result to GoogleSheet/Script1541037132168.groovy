import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper

'Variables'
def jsonSlurper = new JsonSlurper()
_data = CustomKeywords.'pdi.Utilities.getDataRow'(findTestData('Data Files/API/GoogleSheet'), 1)
_paramsBody = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token"
	, _data['client_id'], _data['client_secret'], _data['refresh_token'])

'Access and get accessToken from GoogleSheet'
if(true){
	def response_GoogleAccessToken = WS.sendRequest(findTestObject('API/GoogleSheet/POST_Get GoogleSheet Access Token',
		['p_Body': _paramsBody]))
	_accessToken = jsonSlurper.parseText(response_GoogleAccessToken.getResponseText()).access_token
}

'Get list Test Cases'
if(true){
	def response_ListTCs = WS.sendRequest(findTestObject('API/GoogleSheet/GET_Get list test cases',
		['p_Spreadsheet_Id': _data['spreadsheet_id']
		,'p_SheetName': GlobalVariable.SheetName
		,'p_Authorization': 'Bearer ' + _accessToken]))
	
	'Get list TCs'
	_listTC = []
	def json_ListTCs = jsonSlurper.parseText(response_ListTCs.getResponseText())
	for(TCName in json_ListTCs.values){
		_listTC.add(TCName[0].toString())
	}
}

'Update Test Case status'
if(true){
	if(_listTC.contains(p_TCName)) foundIndex = _listTC.indexOf(p_TCName) + 1
	else {
		foundIndex = _listTC.size() + 1
		'Fill Test Case name'
		WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Set result value',
			['p_Spreadsheet_Id': _data['spreadsheet_id'], 'p_SheetName': GlobalVariable.SheetName
			,'p_Range': 'A' + foundIndex, 'p_ValueRange': p_TCName, 'p_Authorization': 'Bearer ' + _accessToken]))
	}
	rangeValues = String.format('%s","%s","%s","","%s', p_CurrentBuild, p_StartTime, p_Duration, p_TCStatus)
	'Fill test case status'
	WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Set result value',
		['p_Spreadsheet_Id': _data['spreadsheet_id'], 'p_SheetName': GlobalVariable.SheetName
		, 'p_Range': 'B' + foundIndex + ':F' + foundIndex, 'p_ValueRange': rangeValues, 'p_Authorization': 'Bearer ' + _accessToken]))
}