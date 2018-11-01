import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.internal.PathUtil as PathUtil

import internal.GlobalVariable as GlobalVariable


if(p_StartApp) {
	'Get full directory\'s path of android application'
	_appPath = PathUtil.relativeToAbsolutePath(GlobalVariable.Application, RunConfiguration.getProjectDir())
	
	Mobile.startApplication(_appPath, false)
}

Mobile.tap(findTestObject('MOBILE/Login/btn_Account Sign In'), GlobalVariable.ShortTime)

Mobile.tap(findTestObject('MOBILE/Login/cbo_Current Email'), GlobalVariable.ShortTime)

Mobile.tap(findTestObject('MOBILE/Login/lbl_Other item'), GlobalVariable.ShortTime)

Mobile.setText(findTestObject('MOBILE/Login/txt_Email'), p_Email, 0)

Mobile.setText(findTestObject('MOBILE/Login/txt_Password'), p_Password, 0)

Mobile.tap(findTestObject('MOBILE/Login/btn_Sign In'), GlobalVariable.ShortTime)

Mobile.switchToPortrait(FailureHandling.OPTIONAL)

Mobile.tap(findTestObject('MOBILE/Main/btn_No thanks'), 15, FailureHandling.OPTIONAL)

Mobile.tap(findTestObject('MOBILE/Main/btn_Got it'), GlobalVariable.ShortTime, FailureHandling.OPTIONAL)