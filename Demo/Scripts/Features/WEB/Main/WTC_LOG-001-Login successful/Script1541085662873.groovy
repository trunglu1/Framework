import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_testData = findTestData('WEB/Main/WTC_LOG-001-Login successful')
		_totalRow = 1 // For running data single test
		if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
			_totalRow = _testData.getRowNumbers() // For running data driven test
		}
	}
	
	'Open Url'
	if (true) {
		_user = CustomKeywords.'pdi.Utilities.getDataRow'(findTestData('Data Files/WEB/General/Accounts'), 1)
		WebUI.openBrowser(_user['url'])
	}	
}

'Loop around to run script'
for(int i = 1; i <= _totalRow; i++){
	'Get test data'
	if(true){
		_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
	}
	
	'Verify login successful'
	if (true) {
		WebUI.callTestCase(findTestCase('Features/WEB/Main/WTC_LOG-001-Sub-Login successful')
			, [('p_UserName') : _data['username']
			, ('p_Password') : _data['password']])
	}
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
	WebUI.closeBrowser()
}