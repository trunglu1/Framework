import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable
import groovy.util.XmlSlurper

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_testData = findTestData('API/Caculator/ATC_CAL-001- Add function')
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

	'Add two numbers'
	if(true){
		add_function = WS.sendRequest(findTestObject('API/Calculator/SOAP_Add'
			, ['p_Number1' : _data['number1']
			,'p_Number2' : _data['number2']]))
	}
	
	'Verify 200 status code'
	WS.verifyResponseStatusCode(add_function, 200)
	
	'Verify AddResult value by Katalon'
	WS.verifyElementPropertyValue(add_function, 'AddResponse.AddResult', _data['expected'])
	
	'Verify AddResult value by XmlSlurper'
	if(true){
		xml_Response = xmlSlurper.parseText(add_function.getResponseText())
		WS.verifyEqual(xml_Response, _data['expected'])
	}
}	