import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory

import internal.GlobalVariable as GlobalVariable
import io.appium.java_client.MobileDriver

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_testData = findTestData('MOBILE/Main/MTC_LOG-001-Login and logout successful')
		_totalRow = 1 // For running data single test
		if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
			_totalRow = _testData.getRowNumbers() // For running data driven test
		}
	}
	
	'Open application'
	Mobile.callTestCase(findTestCase('Common/MOBILE/Launch application')
		, [('p_PathFile') : GlobalVariable.Application])
}

'Loop around to run script'
for(int i = 1; i <= _totalRow; i++){
	'Get test data'
	if(true){
		_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
	}
	
	'Verify login and logout successful'
	if (true) {
		Mobile.callTestCase(findTestCase('Features/MOBILE/Main/MTC_LOG-001-Sub-Login and logout successful')
			, [('p_Email') : _data['email']
			, ('p_Password') : _data['password']])
	}
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
	CustomKeywords.'pdi.Mobile.ClearMobileEnvironment'()
}