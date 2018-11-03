import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling

import internal.GlobalVariable as GlobalVariable

'Add Trip'
if (true) {
	Mobile.callTestCase(findTestCase('Common/MOBILE/Trip/Add Trip')
		, [('p_Destination') : p_Destination
		, ('p_TripName') : p_TripName
		, ('p_Privacy') : p_Privacy
		, ('p_Purpose') : p_Purpose
		, ('p_Description') : p_Description])
}

'Verify Trip information'
if (true) {
	Mobile.callTestCase(findTestCase('Common/MOBILE/Trip/Verify Trip information')
		, [('p_Destination') : p_Destination
		, ('p_TripName') : p_TripName
		, ('p_Privacy') : p_Privacy
		, ('p_Purpose') : p_Purpose
		, ('p_Description') : p_Description])
}

'Post condition: Not save trip information'
if (true){
	Mobile.tap(findTestObject('MOBILE/Main/img_More Options icon'), GlobalVariable.ShortTime)

	Mobile.tap(findTestObject('MOBILE/Main/btn_Discard'), GlobalVariable.ShortTime, FailureHandling.OPTIONAL)
	
	Mobile.tap(findTestObject('MOBILE/Trip/btn_OK Date'), GlobalVariable.ShortTime)
}
