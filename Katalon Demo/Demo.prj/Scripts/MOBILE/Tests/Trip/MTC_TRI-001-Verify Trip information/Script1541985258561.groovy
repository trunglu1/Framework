import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
	Mobile.callTestCase(findTestCase('MOBILE/Pages/Common/Launch application')
		, [('p_PathFile') : GlobalVariable.Application])
	
	'Login'
	Mobile.callTestCase(findTestCase('MOBILE/Pages/Login/Login to TripIt system')
		, [('p_Email') : GlobalVariable.User, ('p_Password') : GlobalVariable.Password])
}

'Loop around to run script'
for(int i = 1; i <= _totalRow; i++){
	'Get test data'
	if(true){
		_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
	}
	
	'Add Trip'
	if (true) {
		Mobile.callTestCase(findTestCase('MOBILE/Pages/Trip/Add Trip')
			, [('p_Destination') : _data['destination'] 
			, ('p_TripName') : _data['tripName']
			, ('p_Privacy') : _data['privacy']
			, ('p_Purpose') : _data['purpose']
			, ('p_Description') : _data['description']])
	}
	
	'Verify Trip information'
	if (true) {
		Mobile.callTestCase(findTestCase('MOBILE/Pages/Trip/Verify Trip information')
			, [('p_Destination') : _data['destination'] 
			, ('p_TripName') : _data['tripName']
			, ('p_Privacy') : _data['privacy']
			, ('p_Purpose') : _data['purpose']
			, ('p_Description') : _data['description']])
	}
	
	'Post condition: Logout without saving trip information'
	if (true){
		Mobile.tap(findTestObject('MOBILE/Main/img_Home'), GlobalVariable.ShortTime)
		
		Mobile.tap(findTestObject('MOBILE/Main/btn_Discard'), GlobalVariable.ShortTime, FailureHandling.OPTIONAL)
	}
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
	CustomKeywords.'pdi.Mobile.ClearMobileEnvironment'()
}
