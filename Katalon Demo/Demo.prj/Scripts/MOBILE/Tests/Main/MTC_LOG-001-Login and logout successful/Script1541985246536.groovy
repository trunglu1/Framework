import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
	Mobile.callTestCase(findTestCase('MOBILE/Pages/Common/Launch application')
		, [('p_PathFile') : GlobalVariable.Application])
}

'Loop around to run script'
for(int i = 1; i <= _totalRow; i++){
	'Get test data'
	if(true){
		_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
	}
	
	'Login to TripIt system'
	Mobile.callTestCase(findTestCase('MOBILE/Pages/Login/Login to TripIt system')
		, [('p_Email') : _data['email'], ('p_Password') : _data['password']])
	
	'Verify login successful'
	Mobile.verifyElementExist(findTestObject('/MOBILE/Main/btn_Add Trip'), GlobalVariable.LongTime)
	
	'Log out system'
	Mobile.callTestCase(findTestCase('Pages/MOBILE/Log out system'), [:])
	
	'Verify logout successful'
	Mobile.verifyElementExist(findTestObject('MOBILE/Login/btn_Account Sign In'), GlobalVariable.LongTime)
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
	CustomKeywords.'pdi.Mobile.clearMobileEnvironment'()
}