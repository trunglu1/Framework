package pdi

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class WebElements {
	/******************************************************
	 * generate dynamic object by XPath
	 * @author: thangctran
	 * @param xpath : string of xpath to find element
	 * @return The Dynamic object
	 */
	@Keyword
	def generateTestObject(String xpath){
		TestObject dynamic = new TestObject("dynamic")
		dynamic.addProperty('xpath', ConditionType.EQUALS, xpath)
		return dynamic
	}

	/******************************************************
	 * convert TestObject to WebElement
	 * @author: thangctran
	 * @param to: The object to do
	 * @return The WebElement object
	 */
	private WebElement convertToWebElement(TestObject to){
		WebDriver driver = DriverFactory.getWebDriver()
		return driver.findElement(By.xpath(to.findPropertyValue('xpath')))
	}

	/******************************************************
	 * Click on element by Selenium
	 * @author: thangctran
	 * @param to: The object to do
	 * @param clickType: click; doubleClick; rightClick; mouseOver
	 * @return None
	 */
	@Keyword
	def clickSelenium(TestObject to, String clickType='click'){
		//Convert TestObject to WebElement
		WebElement webElement = WebUI.findWebElement(to)

		//Clicking on element
		Actions builder = new Actions(DriverFactory.getWebDriver())
		if (clickType == 'doubleClick') {
			builder.moveToElement(webElement).doubleClick().build().perform()
		} else if (clickType == 'rightClick') {
			builder.moveToElement(webElement).contextClick().build().perform()
		} else if (clickType == 'mouseOver') {
			builder.moveToElement(webElement).build().perform()
		} else {
			builder.moveToElement(webElement).click().perform()
		}
	}

	/******************************************************
	 * click Mouse Position
	 * @author: thangctran
	 * @param to: The object to do
	 * @param x: The x position on object
	 * @param y: The y position on object
	 * @param clickType: click; doubleClick; rightClick; mouseOver
	 * @return None
	 */
	@Keyword
	def clickMousePosition(TestObject to, int x=10, int y=10, String clickType='click'){
		//Convert TestObject to WebElement
		WebElement webElement = WebUI.findWebElement(to)

		//Clicking on element
		Actions builder = new Actions(DriverFactory.getWebDriver())
		if (clickType == 'doubleClick') {
			builder.moveToElement(webElement, x, y).doubleClick().perform()
		} else if (clickType == 'rightClick') {
			builder.moveToElement(webElement, x, y).contextClick().perform()
		} else if (clickType == 'mouseOver') {
			builder.moveToElement(webElement, x, y).perform()
		} else {
			builder.moveToElement(webElement, x, y).click().perform()
		}
	}

	/******************************************************
	 * Click element by java script
	 * @author: thangctran
	 * @param to: The object to do
	 * @param clickType: click; doubleClick; rightClick; mouseOver; scroll
	 * @return None
	 */
	@Keyword
	def clickJS(TestObject to, String clickType='click'){
		//Scroll to element
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement webElement = WebUI.findWebElement(to)
		((JavascriptExecutor) driver).executeScript('(arguments[0]).scrollIntoView();', webElement)

		//Click on element
		if (clickType == 'doubleClick') {
			((JavascriptExecutor) driver).executeScript('(arguments[0]).dblclick();', webElement)
		} else if (clickType == 'rightClick') {
			((JavascriptExecutor) driver).executeScript('(arguments[0]).contextmenu();', webElement)
		} else if (clickType == 'mouseOver') {
			((JavascriptExecutor) driver).executeScript('(arguments[0]).mouseover();', webElement)
		} else if (clickType == 'scroll') {
			((JavascriptExecutor) driver).executeScript('(arguments[0]).scroll();', webElement)
		} else {
			((JavascriptExecutor) driver).executeScript('(arguments[0]).click();', webElement)
		}
	}

	/******************************************************
	 * Set Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @param strDay: The number day text: 01;02;....;31 
	 * @param strMonth: The number month text: 01;02;....;12 
	 * @param strYear: The number year text: 2015;2016;....;2019 
	 * @return None
	 */
	@Keyword
	def setDate(String strName, String strDay, String strMonth, String strYear){
		WebUI.selectOptionByValue(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '1']), strDay, false)
		WebUI.selectOptionByValue(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '2']), strMonth, false)
		WebUI.selectOptionByValue(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '3']), strYear, false)
	}

	/******************************************************
	 * Get Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @return The current date as : DD-MM-YYYY
	 */
	@Keyword
	def getDate(String strName){
		String _currentValue = WebUI.getAttribute(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '1']), 'value')
		_currentValue = _currentValue + "-" + WebUI.getAttribute(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '2']), 'value')
		_currentValue = _currentValue + "-" + WebUI.getAttribute(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '3']), 'value')
		return _currentValue
	}

	/******************************************************
	 * Verify Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @param expectedDay: The expected number day: 01;02;....;31 
	 * @param expectedMonth: The expected number month: 01;02;....;12 
	 * @param expectedYear: The expected number year: 2015;2016;....;2019 
	 * @return None
	 */
	@Keyword
	def verifyDateValue(String strName, String expectedDay, String expectedMonth, String expectedYear){
		String _expectedDate = expectedDay + "-" + expectedMonth + "-" + expectedYear
		WebUI.verifyEqual(getDate(strName), _expectedDate)
	}

	/******************************************************
	 * Set Time element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @param strHour: The number hour text: 00;02;....;23
	 * @param strMinute: The number minute text: 00;02;....;59
	 * @return None
	 */
	@Keyword
	def setTime(String strName, String strHour, String strMinute){
		WebUI.selectOptionByValue(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '1']), strHour, false)
		WebUI.selectOptionByValue(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '2']), strMinute, false)
	}

	/******************************************************
	 * Get Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @return The current time as : HH:MM
	 */
	@Keyword
	def getTime(String strName){
		String _currentValue = WebUI.getAttribute(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '1']), 'value')
		_currentValue = _currentValue + ":" + WebUI.getAttribute(findTestObject('WEB/Common/cbo_DateTime', ['label': strName, 'index': '2']), 'value')
		return _currentValue
	}

	/******************************************************
	 * Verify Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @param expectedHour: The expected hour text: 00;02;....;23
	 * @param expectedMinute: The expected minute text: 00;02;....;59
	 * @return None
	 */
	@Keyword
	def verifyTimeeValue(String strName, String expectedHour, String expectedMinute){
		String _expectedTime = expectedHour + ":" + expectedMinute
		WebUI.verifyEqual(getTime(strName), _expectedTime)
	}
}
