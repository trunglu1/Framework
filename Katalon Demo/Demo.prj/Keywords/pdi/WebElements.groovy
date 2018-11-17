package pdi

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

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

	////////////////////////// Date Time /////////////////////////

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
		WebUI.selectOptionByValue(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '1']), strDay, false)
		WebUI.selectOptionByValue(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '2']), strMonth, false)
		WebUI.selectOptionByValue(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '3']), strYear, false)
	}

	/******************************************************
	 * Get Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @return The current date as : DD-MM-YYYY
	 */
	@Keyword
	def getDate(String strName){
		String _currentValue = WebUI.getAttribute(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '1']), 'value')
		_currentValue = _currentValue + "-" + WebUI.getAttribute(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '2']), 'value')
		_currentValue = _currentValue + "-" + WebUI.getAttribute(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '3']), 'value')
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
		WebUI.selectOptionByValue(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '1']), strHour, false)
		WebUI.selectOptionByValue(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '2']), strMinute, false)
	}

	/******************************************************
	 * Get Date element value
	 * @author: thangctran
	 * @param strName: The label name
	 * @return The current time as : HH:MM
	 */
	@Keyword
	def getTime(String strName){
		String _currentValue = WebUI.getAttribute(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '1']), 'value')
		_currentValue = _currentValue + ":" + WebUI.getAttribute(findTestObject('WEB/Dynamic/cbo_DateTime', ['label': strName, 'index': '2']), 'value')
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

	////////////////////////// List /////////////////////////
	/******************************************************
	 * get list attribute values on list
	 * @author : thangctran
	 * @param obj: The Test object
	 * @param attributeName: The attribute name to get
	 * @return The list attribute value
	 */
	@Keyword
	def getListAttributeValues(TestObject obj, String attributeName){
		def listValues = []
		for (WebElement elementItem: WebUI.findWebElements(obj, GlobalVariable.MediumTime)) {
			listValues.add(elementItem.getAttribute(attributeName).trim())
		}
		return listValues
	}

	/******************************************************
	 * get list attribute values on list
	 * @author : thangctran
	 * @param obj: The Test object
	 * @param attributeName: The attribute name to find
	 * @param attributeValue: The attribute value to find
	 * @return The list attribute value
	 */
	@Keyword
	def findItemIndexOnListByAttribute(TestObject obj, String attributeName, String attributeValue){
		int _findIndex = 0
		int _Count = 0
		for (WebElement elementItem: WebUI.findWebElements(obj, GlobalVariable.MediumTime)) {
			_Count++
			if (elementItem.getAttribute(attributeName).trim() == attributeValue){
				_findIndex = _Count
				break
			}
		}
		return _findIndex
	}

	/******************************************************
	 * select list item by index
	 * @author : thangctran
	 * @param obj: The List Test object
	 * @param index: The index to select
	 * @return None
	 */
	@Keyword
	def selectListItemByIndex(TestObject obj, int index){
		String newXpath = "(" + obj.findPropertyValue("xpath") + ")[" + index + "]"
		WebUI.click(generateTestObject(newXpath))
	}

	/******************************************************
	 * count item on List
	 * @author : thangctran
	 * @param obj: The List Test object
	 * @return The total number of items
	 */
	@Keyword
	def countItemsOnList(TestObject obj){
		WebUI.findWebElements(obj, GlobalVariable.MediumTime).size()
	}

	/******************************************************
	 * get attribute item at index on List
	 * @author : thangctran
	 * @param obj: The List Test object
	 * @param attributeName: The attribute name to get
	 * @param index: The index to select
	 * @return None
	 */
	@Keyword
	def getAttributeItemIndexOnList(TestObject obj, String attributeName, int index){
		String newXpath = "(" + obj.findPropertyValue("xpath") + ")[" + index + "]"
		return WebUI.getAttribute(generateTestObject(newXpath), attributeName).trim()
	}

	////////////////////////// Table //////////////////////////
	/******************************************************
	 * Count columns
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @return The total number of columns
	 */
	@Keyword
	def countColumns(TestObject objTable){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[1]"
		return countItemsOnList(generateTestObject(newXpath))
	}

	/******************************************************
	 * Count rows
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @return The total number of rows
	 */
	@Keyword
	def countRows(TestObject objTable){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr"
		return countItemsOnList(generateTestObject(newXpath))
	}

	/******************************************************
	 * select checkbox on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @param status: The status : check/uncheck
	 * @return None
	 */
	@Keyword
	def setCheckboxOnCell(TestObject objTable, int row, int column, String status='check'){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/input[@type='checkbox']"
		if (status.toLowerCase() == 'check') WebUI.check(generateTestObject(newXpath))
		else WebUI.uncheck(generateTestObject(newXpath))
	}

	/******************************************************
	 * set text on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @param text: The text to set on cell
	 * @return None
	 */
	@Keyword
	def setTextOnCell(TestObject objTable, int row, int column, String text){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/input[@type='text']"
		WebUI.setText(generateTestObject(newXpath), text)
	}

	/******************************************************
	 * select item by label on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @param text: The text item to select on cell
	 * @return None
	 */
	@Keyword
	def selectOptionByLabelOnCell(TestObject objTable, int row, int column, String text){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/select"
		WebUI.selectOptionByLabel(generateTestObject(newXpath), text, false)
	}

	/******************************************************
	 * select item by range index on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @param rangeIndex: The range index to select on cell; Ex: 2; 2,3; 2-5
	 * @return None
	 */
	@Keyword
	def selectOptionByIndexOnCell(TestObject objTable, int row, int column, String rangeIndex){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/select"
		WebUI.selectOptionByIndex(generateTestObject(newXpath), rangeIndex)
	}

	/******************************************************
	 * click on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @return The attribute value
	 */
	@Keyword
	def clickOnCell(TestObject objTable, int row, int column){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]"
		WebUI.click(generateTestObject(newXpath))
	}

	/******************************************************
	 * get text value on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @return The text value of cell
	 */
	@Keyword
	def getTextContentOnCell(TestObject objTable, int row, int column){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]"
		return WebUI.getAttribute(generateTestObject(newXpath), 'textContent').trim()
	}

	/******************************************************
	 * verify text value on column
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param expectedText: The text on cell
	 * @param existing: true(exist) /false (not exist)
	 * @return None
	 */
	@Keyword
	def verifyTextContentOnColumn(TestObject objTable, int column, String expectedText, boolean existing){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[normalize-space(text())='" + expectedText + "'][" + column + "]"
		if (existing) WebUI.verifyElementPresent(generateTestObject(newXpath), GlobalVariable.MediumTime)
		else WebUI.verifyElementNotPresent(generateTestObject(newXpath), GlobalVariable.MediumTime)
	}

	/******************************************************
	 * verify text value on table
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param expectedText: The text on cell
	 * @param existing: true(exist) /false (not exist)
	 * @return None
	 */
	@Keyword
	def verifyTextContentOnTable(TestObject objTable, String expectedText, boolean existing){
		String newXpath = "(" + objTable.findPropertyValue("xpath") + "/tbody/tr/td[normalize-space(text())='" + expectedText + "'])[1]"
		if (existing) WebUI.verifyElementPresent(generateTestObject(newXpath), GlobalVariable.MediumTime)
		else WebUI.verifyElementNotPresent(generateTestObject(newXpath), GlobalVariable.MediumTime)
	}

	/******************************************************
	 * get attribute value on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param row: The row number on Table
	 * @param attributeName: The attribute name to get
	 * @return The attribute value
	 */
	@Keyword
	def getAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]"
		return WebUI.getAttribute(generateTestObject(newXpath), attributeName).trim()
	}


	/******************************************************
	 * get list Attribute values on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @param attributeName: The attribute name to get
	 * @return The list Attribute values on cell
	 */
	@Keyword
	def getListAttributeValuesAtColumn(TestObject objTable, int column, String attributeName){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[" + column + "]/*[1]"
		return getListAttributeValues(generateTestObject(newXpath), attributeName)
	}

	/******************************************************
	 * get list text content values on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column number on Table
	 * @return The list text content values
	 */
	@Keyword
	def getListTextContentAtColumn(TestObject objTable, int column){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[" + column + "]"
		return getListAttributeValues(generateTestObject(newXpath), 'textContent')
	}
}
