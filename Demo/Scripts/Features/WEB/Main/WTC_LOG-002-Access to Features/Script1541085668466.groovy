import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_user = CustomKeywords.'pdi.Utilities.getDataRow'(findTestData('Data Files/WEB/General/Accounts'), 1)
		_testData = findTestData('WEB/Main/WTC_LOG-002-Access to Features')
		_totalRow = 1 // For running data single test 
		if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
			_totalRow = _testData.getRowNumbers() // For running data driven test
		}
	}
	
	'Login'
	if (true) {
		WebUI.callTestCase(findTestCase('Common/WEB/General/Login')
			, [('p_URL') : _user['url']
			, ('p_UserName') : _user['username']
			, ('p_Password') : _user['password']])
	}
}

'Loop around to run script'
for (int i = 1; i <= _totalRow; i++) {
    'Get test data'
    if (true) {
        _data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
    }
	
	'Verify that access to feature successful'
	if (true) {
		WebUI.callTestCase(findTestCase('Features/WEB/Main/WTC_LOG-002-Sub-Access to Features')
			, [('p_MenuItems') : _data['menuItems']
			, ('p_ExpectedTitle') : _data['title']])
	}
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
    WebUI.closeBrowser()
}