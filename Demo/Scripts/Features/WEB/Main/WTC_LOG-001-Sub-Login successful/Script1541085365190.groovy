import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

'Login'
if (true) {
	WebUI.callTestCase(findTestCase('Common/WEB/General/Login')
		, [('p_UserName') : p_UserName
		, ('p_Password') : p_Password])
}
	
'Verify login successful'
WebUI.verifyEqual(WebUI.getWindowTitle(), 'BASE CONTROL MENU')

'Logout'
if (true) {
	WebUI.callTestCase(findTestCase('Common/WEB/General/Select menu')
		, [('p_MenuItems') : 'Utility>LOGOUT'])
}