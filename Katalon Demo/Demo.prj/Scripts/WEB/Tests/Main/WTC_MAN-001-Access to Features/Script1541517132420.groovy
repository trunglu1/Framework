import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_testData = findTestData('WEB/Main/WTC_MAN-001-Access to Features')
		_totalRow = 1 // For running data single test 
		if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
			_totalRow = _testData.getRowNumbers() // For running data driven test
		}
	}
	
	'Login'
	if (true) {
		WebUI.callTestCase(findTestCase('WEB/Pages/Login/Login')
			, [('p_URL') : GlobalVariable.URL
			, ('p_UserName') : GlobalVariable.User
			, ('p_Password') : GlobalVariable.Password])
	}
}

'Loop around to run script'
for (int i = 1; i <= _totalRow; i++) {
    'Get test data'
    if (true) {
        _data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
    }
	 
	 'Select menu'
	 if (true) {
		 WebUI.callTestCase(findTestCase('WEB/Pages/Main/Select menu')
			 , [('p_MenuItems') : _data['menuItems']])
	 }
	 
	 'Verify that access to feature successful'
	 WebUI.verifyEqual(WebUI.getWindowTitle(), _data['title'])
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
    WebUI.callTestCase(findTestCase('WEB/Pages/Common/Close Browser window'), [:])
}