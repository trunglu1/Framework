import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

import internal.GlobalVariable as GlobalVariable

Mobile.tap(findTestObject('MOBILE/Main/img_More Options icon'), GlobalVariable.ShortTime)

Mobile.tap(findTestObject('MOBILE/Main/btn_Sign Out'), GlobalVariable.ShortTime)

Mobile.tap(findTestObject('MOBILE/Trip/btn_OK Date'), GlobalVariable.ShortTime)