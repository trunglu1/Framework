package pdi

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory

import io.appium.java_client.MobileDriver

/********************************* HEADER PART **********************************
 *
 * CUSTOM KEYWORDS NAME		: Utilities.groovy
 * LAST UPDATED     		: Nov 19, 2018
 *
 * CUSTOM KEYWORDS LIST
 *
 *	getCurrentSessionMobileDriver(): Get mobile driver for current session
 *	clearMobileEnvironment(): Clear and reset mobile environment
 */

public class Mobile {
	/******************************************************
	 * Get mobile driver for current session
	 * @return mobile driver for current session
	 */
	@Keyword
	def MobileDriver getCurrentSessionMobileDriver() {
		return MobileDriverFactory.getDriver();
	}

	/******************************************************
	 * Clear and reset mobile environment
	 */
	@Keyword
	def clearMobileEnvironment() {
		MobileDriver _driver = getCurrentSessionMobileDriver()
		_driver.resetApp()
	}
}
