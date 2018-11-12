import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

Mobile.tap(findTestObject('MOBILE/Main/btn_Add Trip'), GlobalVariable.ShortTime)

'Fill Destination'
if (p_Destination != null) {
	Mobile.tap(findTestObject('MOBILE/Trip/lbl_Destination value'), GlobalVariable.ShortTime)
	
	Mobile.setText(findTestObject('MOBILE/Trip/txt_Destination'), p_Destination, 0)
	
	Mobile.tap(findTestObject('MOBILE/Trip/btn_OK'), GlobalVariable.ShortTime)
}

Mobile.tap(findTestObject('MOBILE/Trip/lbl_Start Date value'), GlobalVariable.ShortTime)

Mobile.tap(findTestObject('MOBILE/Trip/btn_OK Date'), GlobalVariable.ShortTime)

Mobile.tap(findTestObject('MOBILE/Trip/lbl_End Date value'), GlobalVariable.ShortTime)

//Mobile.tapAndHold(findTestObject('MOBILE/Trip/btn_2019'), 0, 0)

Mobile.tap(findTestObject('MOBILE/Trip/btn_OK Date'), GlobalVariable.ShortTime)

'Fill Trip Name'
if (p_TripName != null) {
	Mobile.tap(findTestObject('MOBILE/Trip/lbl_Trip Name value'), GlobalVariable.ShortTime)
	
	Mobile.setText(findTestObject('MOBILE/Trip/txt_Trip Name'), p_TripName, 0)
	
	Mobile.tap(findTestObject('MOBILE/Trip/btn_OK'), GlobalVariable.ShortTime)
}

if(p_Privacy == 'check') Mobile.checkElement(findTestObject('MOBILE/Trip/chk_Privacy'), 0)
else Mobile.uncheckElement(findTestObject('MOBILE/Trip/chk_Privacy'), 0)

if(p_Purpose == 'check') Mobile.checkElement(findTestObject('MOBILE/Trip/chk_Purpose'), 0)
else Mobile.uncheckElement(findTestObject('MOBILE/Trip/chk_Purpose'), 0)

'Fill Description'
if (true) {
	Mobile.tap(findTestObject('MOBILE/Trip/lbl_Description value'), GlobalVariable.ShortTime)
	
	Mobile.setText(findTestObject('MOBILE/Trip/txt_Description'), p_Description, 0)
	
	Mobile.tap(findTestObject('MOBILE/Trip/btn_OK'), GlobalVariable.ShortTime)
}	