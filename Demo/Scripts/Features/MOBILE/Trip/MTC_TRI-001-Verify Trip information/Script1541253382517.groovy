import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	'Get test data file'
	if (true) {
		_user = CustomKeywords.'pdi.Utilities.getDataRow'(findTestData('Data Files/MOBILE/General/Trip Accounts'), 1)
		_testData = findTestData('Data Files/MOBILE/Trip/MTC_TRI-001-Verify Trip information')
		_totalRow = 1 // For running data single test
		if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
			_totalRow = _testData.getRowNumbers() // For running data driven test
		}
	}
	
	'Open application'
	Mobile.callTestCase(findTestCase('Common/MOBILE/Launch application')
		, [('p_PathFile') : GlobalVariable.Application])
	
	'Login'
	Mobile.callTestCase(findTestCase('Common/MOBILE/Login to TripIt system')
		, [('p_Email') : _user['email'], ('p_Password') : _user['password']])
}

'Loop around to run script'
for(int i = 1; i <= _totalRow; i++){
	'Get test data'
	if(true){
		_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
	}

	'Verify Trip information'
	if (true) {
		Mobile.callTestCase(findTestCase('Features/MOBILE/Trip/MTC_TRI-001-Sub-Verify Trip information')
			, [('p_Destination') : _testData['destination'] 
			, ('p_TripName') : _testData['tripName']
			, ('p_Privacy') : _testData['privacy']
			, ('p_Purpose') : _testData['purpose']
			, ('p_Description') : _testData['description']])
	}
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
	Mobile.closeApplication()
}
