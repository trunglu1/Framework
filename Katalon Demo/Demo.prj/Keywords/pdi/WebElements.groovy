package pdi

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.awt.Color
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

/********************************* HEADER PART **********************************
 *
 * CUSTOM KEYWORDS NAME		: Elements.groovy
 * LAST UPDATED     			: Nov 19, 2018
 *
 * CUSTOM KEYWORDS LIST
 *
 * *********** General ***********
 *	generateTestObject(String xpath): Generate dynamic object by XPath
 *	convertToWebElement(TestObject to): convert TestObject to WebElement
 *	convertRGBtoHEX(String rgbFormat): Convert rgb to hex code color
 *	verifyElementColor(TestObject to, String attributeValue, String expectedColor): Verify Element color
 *	clickSelenium(TestObject to, String clickType='click'): Click on element by Selenium
 *	clickMousePosition(TestObject to, int x=10, int y=10, String clickType='click'): Click Mouse Position
 *	clickJS(TestObject to, String clickType='click'): Click element by java script
 * executeJavaScript(TestObject to, String method): Execute java script
 * getAttributeByJS(TestObject to, String fullPathNode): Get attribute by java script
 * verifyAttributeByJS(TestObject to, String fullPathNode, String expectedValue): Verify attribute by java script
 *	*********** Date Time ***********
 *	setDate(String strName, String strDay, String strMonth, String strYear): Set Date element value
 *	getDate(String strName): Get Date element value
 *	verifyDateValue(String strName, String expectedDay, String expectedMonth, String expectedYear): Verify Date element value
 *	setTime(String strName, String strHour, String strMinute): Set Time element value
 *	getTime(String strName): Get Date element value
 *	verifyTimeValue(String strName, String expectedHour, String expectedMinute): Verify Date element value
 *	*********** List ***********
 *	getListAttributeValues(TestObject objList, String attributeName): Get list attribute values on list
 *	findItemIndexOnListByAttribute(TestObject objList, String attributeName, String attributeValue): Find item index on list by attribute
 *	selectListItemByIndex(TestObject objList, int index): Select list item by index
 *	selectListItemByText(TestObject objList, String text): Select list item by text
 *	countItemsOnList(TestObject objList): Count item on List
 *	getAttributeItemIndexOnList(TestObject objList, String attributeName, int index): Get attribute item at index on List
 *	*********** Table ***********
 *	countColumns(TestObject objTable): Count columns number
 *	countRows(TestObject objTable): Count rows number
 *	setCheckboxOnCell(TestObject objTable, int row, int column, boolean status=true): Select checkbox on cell
 *	verifyCheckboxStatusOnCell(TestObject objTable, int row, int column, boolean expectedStatus=true):  Verify checkbox status on cell
 *	selectOptionByLabelOnCell(TestObject objTable, int row, int column, String text): Select item by label on cell
 *	selectOptionByIndexOnCell(TestObject objTable, int row, int column, String rangeIndex): Select item by range index on cell
 *	clickOnCell(TestObject objTable, int row, int column): Click on cell
 *	getTextContentOnCell(TestObject objTable, int row, int column): Get text value on cell
 *	verifyTextContentOnColumn(TestObject objTable, int column, String expectedText, boolean existing): Verify text value on column
 *	verifyTextContentOnTable(TestObject objTable, String expectedText, boolean existing): Verify text value on table
 *	getAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName): Get attribute value on cell
 *	verifyAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName, String expectedValue): Verify attribute value on cell
 *	getListAttributeValuesAtColumn(TestObject objTable, int column, String attributeName): Get list Attribute values at column
 * *********** Menu ***********
 * selectMenu(String menuNodes, String delimiter): Select menu items
 */

public class WebElements {

	////////////////////////// General /////////////////////////

	/******************************************************
	 * Generate dynamic object by XPath
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
	 * Convert rgb to hex code color
	 * @author thangctran
	 * @param rgbFormat : The rgb format (Ex: rgb(255,255,255))
	 * @return the hex color (Ex: #ffffff)
	 */
	private convertRGBtoHEX(String rgbFormat) {
		def arrRGB = rgbFormat.replace("(", ",").replace(")", "").split(",");
		Color c = new Color(Integer.valueOf(arrRGB[1].trim()),Integer.valueOf(arrRGB[2].trim()),Integer.valueOf(arrRGB[3].trim()));
		String hexFormat = '#' + Integer.toHexString( c.getRGB() & 0x0000FFFFFFF);
		return hexFormat.replaceFirst('f', '').toLowerCase();
	}

	/******************************************************
	 * Verify Element color
	 * Author: thangctran
	 * @param to : The object to do
	 * @param attributeName : the attribute name of element: color; border-left-color; background-color
	 * @param expectedColor : the expected color (Ex: #ffffff)
	 * @return None
	 */
	@Keyword
	def verifyElementColor(TestObject to, String attributeName, String expectedColor){
		String currentColor = convertRGBtoHEX(WebUI.getCSSValue(to, attributeName))
		WebUI.verifyEqual(currentColor, expectedColor)
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
	 * Click Mouse Position
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
	 * Execute java script
	 * @author: thangctran
	 * @param to: The object to do
	 * @param method: The method to execute: click(); scrollIntoView()
	 * @return None
	 */
	@Keyword
	def executeJavaScript(TestObject to, String method){
		WebElement webElement = WebUI.findWebElement(to)
		String _textJS = String.format("arguments[0].%s;", method)
		((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript(_textJS, webElement)
	}

	/******************************************************
	 * Get attribute by java script
	 * @author: thangctran
	 * @param to: The object to do
	 * @param fullPathNode: The full path node to get value (form.classList.value)
	 * @return The attribute value
	 */
	@Keyword
	def getAttributeByJS(TestObject to, String fullPathNode){
		WebElement webElement = WebUI.findWebElement(to)
		String _textJS = String.format("return arguments[0].%s;", fullPathNode)
		return ((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript(_textJS, webElement).toString().trim()
	}

	/******************************************************
	 * Verify attribute by java script
	 * @author: thangctran
	 * @param to: The object to do
	 * @param fullPathNode: The full path node to get value (options[arguments[1].selectedIndex].text)
	 * @param expectedValue: The expected value
	 * @return The attribute value
	 */
	@Keyword
	def verifyAttributeByJS(TestObject to, String fullPathNode, String expectedValue){
		String currentValue = getAttributeByJS(to, fullPathNode)
		WebUI.verifyEqual(currentValue, expectedValue)
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
	def verifyTimeValue(String strName, String expectedHour, String expectedMinute){
		String _expectedTime = expectedHour + ":" + expectedMinute
		WebUI.verifyEqual(getTime(strName), _expectedTime)
	}

	////////////////////////// List /////////////////////////
	/******************************************************
	 * Get list attribute values on list
	 * @author : thangctran
	 * @param objList: The Test object
	 * @param attributeName: The attribute name to get
	 * @return The list attribute value
	 */
	@Keyword
	def getListAttributeValues(TestObject objList, String attributeName){
		def listValues = []
		for (WebElement elementItem: WebUI.findWebElements(objList, GlobalVariable.MediumTime)) {
			listValues.add(elementItem.getAttribute(attributeName).trim())
		}
		return listValues
	}

	/******************************************************
	 * Find item index on list by attribute
	 * @author : thangctran
	 * @param objList: The Test object
	 * @param attributeName: The attribute name to find
	 * @param attributeValue: The attribute value to find
	 * @return The list attribute value
	 */
	@Keyword
	def findItemIndexOnListByAttribute(TestObject objList, String attributeName, String attributeValue){
		int _findIndex = 0
		int _Count = 0
		for (WebElement elementItem: WebUI.findWebElements(objList, GlobalVariable.MediumTime)) {
			_Count++
			if (elementItem.getAttribute(attributeName).trim() == attributeValue){
				_findIndex = _Count
				break
			}
		}
		return _findIndex
	}

	/******************************************************
	 * Select list item by index
	 * @author : thangctran
	 * @param objList: The List Test object
	 * @param index: The index to select
	 * @return None
	 */
	@Keyword
	def selectListItemByIndex(TestObject objList, int index){
		String newXpath = "(" + objList.findPropertyValue("xpath") + ")[" + index + "]"
		WebUI.click(generateTestObject(newXpath))
	}

	/******************************************************
	 * Select list item by text
	 * @author : thangctran
	 * @param objList: The List Test object
	 * @param text: The text of item to select
	 * @return None
	 */
	@Keyword
	def selectListItemByText(TestObject objList, String text){
		String newXpath = objList.findPropertyValue("xpath") + "[normalize-space(.)='" + text + "']"
		WebUI.click(generateTestObject(newXpath))
	}

	/******************************************************
	 * Count item on List
	 * @author : thangctran
	 * @param objList: The List Test object
	 * @return The total number of items
	 */
	@Keyword
	def countItemsOnList(TestObject objList){
		WebUI.findWebElements(objList, GlobalVariable.MediumTime).size()
	}

	/******************************************************
	 * Get attribute item at index on List
	 * @author : thangctran
	 * @param objList: The List Test object
	 * @param attributeName: The attribute name to get
	 * @param index: The index to select
	 * @return None
	 */
	@Keyword
	def getAttributeItemIndexOnList(TestObject objList, String attributeName, int index){
		String newXpath = "(" + objList.findPropertyValue("xpath") + ")[" + index + "]"
		return WebUI.getAttribute(generateTestObject(newXpath), attributeName).trim()
	}

	////////////////////////// Table //////////////////////////
	/******************************************************
	 * Count columns number
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
	 * Count rows number
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
	 * Select checkbox on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @param status: The status : true(check)/ false(uncheck)
	 * @return None
	 */
	@Keyword
	def setCheckboxOnCell(TestObject objTable, int row, int column, boolean status=true){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/input[@type='checkbox']"
		if (status) WebUI.check(generateTestObject(newXpath))
		else WebUI.uncheck(generateTestObject(newXpath))
	}

	/******************************************************
	 * Verify checkbox status on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @param expectedStatus: The expected status : true(check)/ false(uncheck)
	 * @return None
	 */
	@Keyword
	def verifyCheckboxStatusOnCell(TestObject objTable, int row, int column, boolean expectedStatus=true){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/input[@type='checkbox']"
		boolean currentStatus = WebUI.verifyElementChecked(generateTestObject(newXpath), GlobalVariable.MediumTime, FailureHandling.OPTIONAL)
		WebUI.verifyEqual(currentStatus, expectedStatus)
	}

	/******************************************************
	 * Set text on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @param text: The text to set on cell
	 * @return None
	 */
	@Keyword
	def setTextOnCell(TestObject objTable, int row, int column, String text){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/input[@type='text']"
		WebUI.setText(generateTestObject(newXpath), text)
	}

	/******************************************************
	 * Select item by label on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @param text: The text item to select on cell
	 * @return None
	 */
	@Keyword
	def selectOptionByLabelOnCell(TestObject objTable, int row, int column, String text){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/select"
		WebUI.selectOptionByLabel(generateTestObject(newXpath), text, false)
	}

	/******************************************************
	 * Select item by range index on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @param rangeIndex: The range index to select on cell; Ex: 2; 2,3(select items 2 and 3); 2-5 (select items from 2 to 5)
	 * @return None
	 */
	@Keyword
	def selectOptionByIndexOnCell(TestObject objTable, int row, int column, String rangeIndex){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/select"
		WebUI.selectOptionByIndex(generateTestObject(newXpath), rangeIndex)
	}

	/******************************************************
	 * Click on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @return The attribute value
	 */
	@Keyword
	def clickOnCell(TestObject objTable, int row, int column){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]"
		WebUI.click(generateTestObject(newXpath))
	}

	/******************************************************
	 * Get text value on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @return The text value of cell
	 */
	@Keyword
	def getTextContentOnCell(TestObject objTable, int row, int column){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]"
		return WebUI.getAttribute(generateTestObject(newXpath), 'textContent').trim()
	}

	/******************************************************
	 * Verify text value on column
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column index on Table
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
	 * Verify text value on table
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param expectedText: The text on cell
	 * @param existing: true(exist) /false (not exist)
	 * @return None
	 */
	@Keyword
	def verifyTextContentOnTable(TestObject objTable, String expectedText, boolean existing){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[normalize-space(text())='" + expectedText + "']"
		if (existing) WebUI.verifyElementPresent(generateTestObject(newXpath), GlobalVariable.MediumTime)
		else WebUI.verifyElementNotPresent(generateTestObject(newXpath), GlobalVariable.MediumTime)
	}

	/******************************************************
	 * Get attribute value on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Table
	 * @param attributeName: The attribute name to get
	 * @return The attribute value
	 */
	@Keyword
	def getAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]"
		return WebUI.getAttribute(generateTestObject(newXpath), attributeName).trim()
	}

	/******************************************************
	 * Verify attribute value on cell
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param row: The row index on Table
	 * @param column: The column index on Tablee
	 * @param attributeName: The attribute name to verify
	 * @param expectedValue: The expected attribute value to verify
	 * @return None
	 */
	@Keyword
	def verifyAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName, String expectedValue){
		String currentValue = getAttributeValueOnCell(objTable, row, column, attributeName)
		WebUI.verifyEqual(currentValue, expectedValue)
	}

	/******************************************************
	 * Get list Attribute values at column
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column index on Table
	 * @param attributeName: The attribute name to get
	 * @return The list Attribute values on cell
	 */
	@Keyword
	def getListAttributeValuesAtColumn(TestObject objTable, int column, String attributeName){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[" + column + "]/*[1]"
		return getListAttributeValues(generateTestObject(newXpath), attributeName)
	}

	/******************************************************
	 * Get list text content values at column
	 * @author : thangctran
	 * @param objTable: The Table object
	 * @param column: The column index on Table
	 * @return The list text content values
	 */
	@Keyword
	def getListTextContentAtColumn(TestObject objTable, int column){
		String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[" + column + "]"
		return getListAttributeValues(generateTestObject(newXpath), 'textContent')
	}

	////////////////////////// Menu //////////////////////////
	/******************************************************
	 * Select menu items
	 * @author : thangctran
	 * @param menuNodes: The menu node to select
	 * @param delimiter: The delimiter text to slipt menu
	 * @return None
	 */
	@Keyword
	def selectMenu(String menuNodes, String delimiter='>'){
		def _listItems = menuNodes.split(delimiter)
		//Select the first main item
		WebUI.click(findTestObject('WEB/Dynamic/lnk_Link', [('label') : _listItems[0]]))
		//Mouse over on item
		int i
		for (i=1 ; i<_listItems.size(); i++) {
			WebUI.waitForElementVisible(findTestObject('WEB/Dynamic/lnk_Link', [('label') : _listItems[i]]), GlobalVariable.MediumTime)
			WebUI.mouseOver(findTestObject('WEB/Dynamic/lnk_Link', [('label') : _listItems[i]]))
		}
		//Select on the latest item
		WebUI.click(findTestObject('WEB/Dynamic/lnk_Link', [('label') : _listItems[i-1]]))

		WebUI.waitForPageLoad(GlobalVariable.LongTime)
	}
}
