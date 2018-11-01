import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling

Mobile.callTestCase(findTestCase('Common/MOBILE/Login to TripIt system')
	, [('p_Email') : 'collect.report2018@gmail.com', ('p_Password') : 'kms@2018'])

Mobile.verifyElementExist(findTestObject('/MOBILE/Main/btn_Add Trip'), GlobalVariable.LongTime)

Mobile.callTestCase(findTestCase('Common/MOBILE/Log out system'), [:], FailureHandling.STOP_ON_FAILURE)

Mobile.verifyElementExist(findTestObject('MOBILE/Login/btn_Account Sign In'), GlobalVariable.LongTime)