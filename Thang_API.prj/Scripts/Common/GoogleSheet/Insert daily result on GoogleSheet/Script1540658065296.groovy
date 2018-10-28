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

'Insert daily result to GoogleSheet'
if(true){
	WS.sendRequest(findTestObject('Object Repository/GoogleSheet/Insert daily result column',
		['spreadsheet_id': _data['spreadsheet_id']
		,'sheetId': _data[GlobalVariable.sheetName]
		,'authorization': 'Bearer ' + accessToken]))
}