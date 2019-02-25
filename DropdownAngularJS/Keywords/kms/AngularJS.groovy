package kms
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class AngularJS {

	/**
	 * Verify the expected items names on Dropdown
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param expectedName : the expected item names to verify
	 */
	@Keyword
	def verifyOptionSelectedByName(TestObject dropdownObject, String expectedName) {
		WebElement element = WebUI.findWebElement(dropdownObject)
		String currentSelected = element.getAttribute('innerText').trim()
		if (currentSelected == expectedName) KeywordUtil.markPassed("Items [" + expectedName + "] are selected")
		else KeywordUtil.markFailed("Actual [" + currentSelected + "] - Expetced [" + expectedName + "] are not match")
	}

	/**
	 * Verify Items status on Dropdown by Names
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param listItemNames : the list item names to verify
	 Example: itemName1; itemName1,itemName2; itemName1,...,itemNameN
	 * @param expectedStatus : enabled (true); disabled (false)
	 */
	@Keyword
	def verifyOptionItemsStatus(TestObject dropdownObject, String listItemNames, boolean expectedStatus=true) {
		List<WebElement> webElements = getListItemsOnOption(dropdownObject, listItemNames)
		boolean checkStatus = true

		//Verify All items status
		for (WebElement webCheckbox : webElements) {
			WebUI.comment("Status: " + webCheckbox.getAttribute('aria-disabled').toString())
			if ((webCheckbox.getAttribute('aria-disabled') != "false") == expectedStatus) checkStatus = false
		}

		//Close the popup Option
		webElements.get(0).sendKeys(Keys.chord(Keys.ESCAPE))

		//Wait for the popup disappear
		Thread.sleep(1000)

		//Verify items status
		String logStatus = "disabled"
		if (expectedStatus) logStatus = "anabled"
		if (checkStatus) KeywordUtil.markPassed("Items [" + listItemNames + "] are " + logStatus)
		else KeywordUtil.markFailed("Items [" + listItemNames + "] are not " + logStatus)
	}

	private String convertStatus(boolean status){
		if(status) return "Select"
		else return "Deselect"
	}

	/**
	 * Get list webElement items on Dropdown
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param listItems : null(All), index list or Name range of the Dropdown to select
	 * @param optionGroupName : the option group name
	 * @return the list webElemnts
	 */
	private List<WebElement> getListItemsOnOption(TestObject dropdownObject, Object listItems=null, String optionGroupName=null){
		try {
			//Convert TestObject to WebElement
			WebDriver driver = DriverFactory.getWebDriver()
			WebElement element = WebUI.findWebElement(dropdownObject)
			String tagName = element.getAttribute('localName').split('-')[0]

			//Open the popup Option
			element.click()

			//Define xpath
			String xpathItems = String.format("(//*[starts-with(@style, 'transform-origin:')])[last()]/descendant::%s-option", tagName)
			if(optionGroupName != null) xpathItems = String.format("(//*[starts-with(@style, 'transform-origin:')])[last()]/descendant::%s-optgroup[./label[normalize-space(text())='%s']]/%s-option", tagName, optionGroupName, tagName)

			//Waiting for 3s to the Popup appear
			Thread.sleep(1500)
			if(driver.findElements(By.xpath(xpathItems)).size() == 0) {
				element.click()
				Thread.sleep(1500)
			}

			//Find All Items
			def returnElements = []
			if(listItems == null) returnElements = driver.findElements(By.xpath(xpathItems))
			//Find Items by Name
			else if (listItems instanceof String){
				def itemNames = listItems.split(",")
				for(String itemName : itemNames){
					String newXpath = xpathItems + "[normalize-space(.)='" + itemName + "']"
					returnElements.add(driver.findElement(By.xpath(newXpath)))
				}
			}
			//Find Items by Index
			else {
				for (int index : listItems) {
					String newXpath = xpathItems + "[" + index + "]"
					returnElements.add(driver.findElement(By.xpath(newXpath)))
				}
			}

			return returnElements
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("Dropdown not found: " + e.message)
			return null
		}
	}

	/**
	 * Select items on Dropdown
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param range : null(All), index or name range of the Dropdown to select. Index starts from 1.
	 Example:
	 2 - index 2
	 "2,3" - index 2 and 3
	 "2-5" - index 2 to 5 (2, 3, 4, 5)
	 * @param isSelect : select/check (true); deselect/uncheck (false)
	 * @param optionGroupName : the option group name
	 */
	private selectDropdown(TestObject dropdownObject, Object range, boolean isSelect=true, String optionGroupName=null) {
		try {
			List<WebElement> webElements = getListItemsOnOption(dropdownObject, range, optionGroupName)

			//Does Checkbox item or not
			String classInfo = webElements.get(0).getAttribute('class')
			boolean isCheckbox = false
			if(classInfo.contains("multiple") || classInfo.contains("checkbox")) isCheckbox = true

			//Select All items
			int i
			for (i = 0; i < webElements.size(); i++) {
				if ((webElements.get(i).getAttribute('aria-selected') == "true") != isSelect) {
					if(isCheckbox) webElements.get(i).click()
					else webElements.get(i).sendKeys(Keys.chord(Keys.SPACE))
				}
			}

			//Close the Popup Option if has checkbox item
			if(isCheckbox) webElements.get(i - 1).sendKeys(Keys.chord(Keys.ESCAPE))

			// Wait for the Popup disappear
			Thread.sleep(1500)
			KeywordUtil.markPassed("Dropdown has been selected")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("Dropdown Item not found: " + e.message)
		}
	}

	/**
	 * Select Items on Dropdown by Index
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param range : index range of the Dropdown to select. Index starts from 1.
	 Example:
	 2 - index 2
	 "2,3" - index 2 and 3
	 "2-5" - index 2 to 5 (2, 3, 4, 5)
	 * @param isSelect : select/check (true); deselect/uncheck (false)
	 */
	@Keyword
	def selectOptionByIndex(TestObject dropdownObject, Object range, boolean isSelect=true) {
		Integer[] indexes = WebUiCommonHelper.indexRangeToArray(String.valueOf(range))
		KeywordUtil.logInfo(convertStatus(isSelect) + " Items on Dropdown by Index: " + indexes.toString())
		selectDropdown(dropdownObject, indexes, isSelect)
	}

	/**
	 * Select Items on Dropdown by Name
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param listItemNames : the list item/checkbox names to select
	 Example: itemName1; itemName1,itemName2; itemName1,...,itemNameN
	 * @param isSelect : select/check (true); deselect/uncheck (false)
	 */
	@Keyword
	def selectOptionByName(TestObject dropdownObject, String listItemNames, boolean isSelect=true) {
		KeywordUtil.logInfo(convertStatus(isSelect) + " Items on Dropdown by Name: [" + listItemNames + "]")
		selectDropdown(dropdownObject, listItemNames, isSelect)
	}

	/**
	 * Select Sub Items on Dropdown by Index
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param optionGroupName : the option group name
	 * @param range : index range of the Dropdown to select. Index starts from 1.
	 Example:
	 2 - index 2
	 "2,3" - index 2 and 3
	 "2-5" - index 2 to 5 (2, 3, 4, 5)
	 * @param isSelect : select/check (true); deselect/uncheck (false)
	 */
	@Keyword
	def selectSubOptionByIndex(TestObject dropdownObject, String optionGroupName, Object range, boolean isSelect=true) {
		Integer[] indexes = WebUiCommonHelper.indexRangeToArray(String.valueOf(range))
		KeywordUtil.logInfo(convertStatus(isSelect) + " Sub Items on Dropdown by Index: " + indexes.toString())
		selectDropdown(dropdownObject, indexes, isSelect, optionGroupName)
	}

	/**
	 * Select Sub Items on Dropdown by Names
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param optionGroupName : the option group name
	 * @param listItemNames : the list item names to select
	 Example: itemName1; itemName1,itemName2; itemName1,...,itemNameN
	 * @param isSelect : select/check (true); deselect/uncheck (false)
	 */
	@Keyword
	def selectSubOptionByName(TestObject dropdownObject, String optionGroupName, String listItemNames, boolean isSelect=true) {
		KeywordUtil.logInfo(convertStatus(isSelect) + " Sub Items on Dropdown by Name: [" + listItemNames + "]")
		selectDropdown(dropdownObject, listItemNames, isSelect, optionGroupName)
	}

	/**
	 * Select All items on Dropdown
	 * @param dropdownObject : Dropdown test object (AngularJS)
	 * @param isSelect : select/check (true); deselect/uncheck (false)
	 */
	@Keyword
	def selectAllOption(TestObject dropdownObject, boolean isSelect=true) {
		KeywordUtil.logInfo(convertStatus(isSelect) + " All Items on Dropdown")
		selectDropdown(dropdownObject, null, isSelect)
	}
}