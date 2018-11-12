import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

if(p_Destination != null) Mobile.verifyElementText(findTestObject('MOBILE/Trip/lbl_Destination value'), p_Destination)
if(p_TripName != null) Mobile.verifyElementText(findTestObject('MOBILE/Trip/lbl_Trip Name value'), p_TripName)
if(p_Description != null) Mobile.verifyElementText(findTestObject('MOBILE/Trip/lbl_Description value'), p_Description)

if(p_Privacy == 'check') Mobile.verifyElementChecked(findTestObject('MOBILE/Trip/chk_Privacy'), 0)
else Mobile.verifyElementNotChecked(findTestObject('MOBILE/Trip/chk_Privacy'), 0)

if(p_Purpose == 'check') Mobile.verifyElementChecked(findTestObject('MOBILE/Trip/chk_Purpose'), 0)
else Mobile.verifyElementNotChecked(findTestObject('MOBILE/Trip/chk_Purpose'), 0)
