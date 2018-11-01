import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


if(p_URL != null) WebUI.openBrowser(p_URL)

if(p_UserName != null) WebUI.setText(findTestObject('WEB/Login/txt_User name'), p_UserName)

if(p_Password != null) WebUI.setText(findTestObject('WEB/Login/txt_Password'), p_Password)

WebUI.click(findTestObject('WEB/Login/btn_Login'))

WebUI.waitForPageLoad(GlobalVariable.LongTime)