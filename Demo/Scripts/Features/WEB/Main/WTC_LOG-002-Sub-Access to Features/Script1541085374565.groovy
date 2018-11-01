import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

'Select menu'
if (true) {
	WebUI.callTestCase(findTestCase('Common/WEB/General/Select menu')
		, [('p_MenuItems') : p_MenuItems])
}

'Verify that access to feature successful'
if (true) {
	WebUI.verifyEqual(WebUI.getWindowTitle(), p_ExpectedTitle)
}