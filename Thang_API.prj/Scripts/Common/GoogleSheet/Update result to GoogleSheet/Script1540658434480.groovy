import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper

'Variables'
def jsonSlurper = new JsonSlurper()
_data = CustomKeywords.'helper.Utilities.getDataRow'('Data Files/GoogleSheets', 1)
paramsBody = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token"
	, _data['client_id'], _data['client_secret'], _data['refresh_token'])

'Access and get accessToken from GoogleSheet'
if(true){
	def response_GoogleAccessToken = WS.sendRequest(findTestObject('GoogleSheet/Get GoogleSheet Access Token',
		['body': paramsBody]))
	accessToken = jsonSlurper.parseText(response_GoogleAccessToken.getResponseText()).access_token
}

'Get list Test Cases'
if(true){
	def response_ListTCs = WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Get list test cases',
		['spreadsheet_id': _data['spreadsheet_id']
		,'sheetName': GlobalVariable.sheetName
		,'authorization': 'Bearer ' + accessToken]))
	
	'Get list TCs'
	listTC = []
	def json_ListTCs = jsonSlurper.parseText(response_ListTCs.getResponseText())
	for(TCName in json_ListTCs.values){
		listTC.add(TCName[0].toString())
	}
}

'Update Test Case status'
if(true){
	if(listTC.contains(testCaseName)) foundIndex = listTC.indexOf(testCaseName) + 1
	else {
		foundIndex = listTC.size() + 1
		'Fill Test Case name'
		WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Set result value',
			['spreadsheet_id': _data['spreadsheet_id'], 'sheetName': GlobalVariable.sheetName
			,'range': 'A' + foundIndex, 'valueRange': testCaseName, 'authorization': 'Bearer ' + accessToken]))
	}
	rangeValues = String.format('%s","%s","%s","","%s', currentBuild, startTime, duration, testCaseStatus)
	'Fill current build'
	WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Set result value',
		['spreadsheet_id': _data['spreadsheet_id'], 'sheetName': GlobalVariable.sheetName
		, 'range': 'B' + foundIndex + ':F' + foundIndex, 'valueRange': rangeValues, 'authorization': 'Bearer ' + accessToken]))
}