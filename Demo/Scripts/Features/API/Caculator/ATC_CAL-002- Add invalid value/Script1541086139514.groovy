import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import groovy.util.XmlSlurper

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_testData = findTestData('API/Caculator/ATC_CAL-002- Add invalid value')
		_totalRow = 1 // For running data single test
		if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
			_totalRow = _testData.getRowNumbers() // For running data driven test
		}
	}
}

'Loop around to run script'
def xmlSlurper = new XmlSlurper()
def add_function
for(int i = 1; i <= _totalRow; i++){
	'Get test data'
	if(true){
		_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
	}

	'Add invalid number data'
	if(true){
		add_function = WS.sendRequest(findTestObject('API/Calculator/SOAP_Add'
			, ['p_Number1' : _data['number1']
			,'p_Number2' : _data['number2']]))
	}
	
	'Verify 500 status code'
	WS.verifyResponseStatusCode(add_function, 500)
	
	'Verify Error message by XmlSlurper'
	if(true){
		errorMessage = ".*Error in deserializing body of request message for operation 'Add'. The value 'text' cannot be parsed as the type 'Int32'.Input string was not in a correct format.*"
		xml_Response = xmlSlurper.parseText(add_function.getResponseText()).toString()
		WS.verifyMatch(xml_Response, errorMessage, true)
	}
}	