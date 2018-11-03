import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling

import internal.GlobalVariable as GlobalVariable

Mobile.callTestCase(findTestCase('Common/MOBILE/Login to TripIt system')
	, [('p_Email') : p_Email, ('p_Password') : p_Password])

Mobile.verifyElementExist(findTestObject('/MOBILE/Main/btn_Add Trip'), GlobalVariable.LongTime)

Mobile.callTestCase(findTestCase('Common/MOBILE/Log out system'), [:], FailureHandling.STOP_ON_FAILURE)

Mobile.verifyElementExist(findTestObject('MOBILE/Login/btn_Account Sign In'), GlobalVariable.LongTime)