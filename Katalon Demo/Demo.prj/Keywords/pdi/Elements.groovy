package pdi

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.By

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class Elements {
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
}
