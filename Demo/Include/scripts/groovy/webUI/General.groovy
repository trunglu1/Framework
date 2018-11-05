package webUI

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When

class General {
	/**
	 * The step definitions below match with Katalon sample Gherkin steps
	 */
	@Given('User login PDI with valid email "(.*)" and password "(.*)"$')
	def loginToPDI(String user, String password) {
		WebUI.openBrowser('https://pdi.intellifuel.net/login.php?operator1')

		WebUI.callTestCase(findTestCase('Common/WEB/General/Login')
				, [('p_UserName') : user
					, ('p_Password') : password])
	}

	@When('User selects the menu item "(.*)"$')
	def selectMenuItems(String menuItem) {
		WebUI.callTestCase(findTestCase('Common/WEB/General/Select menu')
				, [('p_MenuItems') : menuItem])
	}

	@Then('Verify the window title "(.*)"$')
	def verifyWindowTitle(String expectedTitle) {
		WebUI.verifyEqual(WebUI.getWindowTitle(), expectedTitle)
	}

	@Then('Clear environment')
	def clearEnvironment() {
		WebUI.closeBrowser()
	}
}