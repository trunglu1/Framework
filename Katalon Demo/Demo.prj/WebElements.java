package kms;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.kms.katalon.core.logging.KeywordLogger;
import com.kms.katalon.core.util.KeywordUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import com.kms.katalon.core.testobject.ConditionType;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webui.driver.DriverFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/********************************* HEADER PART **********************************
 *
 * CUSTOM KEYWORDS NAME		: Elements.groovy
 * LAST UPDATED     			: Nov 19, 2018
 *
 * CUSTOM KEYWORDS LIST
 *
 * *********** General ***********
 *  waitForPresent(String strXpath, int iTimeOut): Get existing of element
 *	generateTestObject(String xpath): Generate dynamic object by XPath
 *	convertToWebElement(TestObject to): convert TestObject to WebElement
 *  verifyExistingByXpath(String strXpath, int iTimeOut, Boolean existing): Verify existing element by XPath
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
 *  verifyListAttributeValues(TestObject objList, String attributeName, List<String> expectedListValues): : Verify list attribute values on list
 *	findItemIndexOnListByAttribute(TestObject objList, String attributeName, String attributeValue): Find item index on list by attribute
 *  verifyItemExistingOnListByAttribute(TestObject objList, String attributeName, String expectedValue, Boolean existing): Verify item existing on list
 *	selectListItemByIndex(TestObject objList, int index): Select list item by index
 *	selectListItemByText(TestObject objList, String text): Select list item by text
 *	countItemsOnList(TestObject objList): Count item on List
 *	getAttributeItemIndexOnList(TestObject objList, String attributeName, int index): Get attribute item at index on List
 *	*********** Table ***********
 *	countColumns(TestObject objTable): Count columns number
 *	countRows(TestObject objTable): Count rows number
 * 	getListHeaderColumns(TestObject objTable): Get list header column values on Table
 * 	verifyHeaderColumnExisting(TestObject objTable, String headerName, int iTimeOut, Boolean existing): Verify header column existing on Table
 * 	selectHeaderColumn(TestObject objTable, String headerName): Select header column existing on Table
 *	setCheckboxOnCell(TestObject objTable, int row, int column, boolean status=true): Select checkbox on cell
 *	verifyCheckboxStatusOnCell(TestObject objTable, int row, int column, boolean expectedStatus=true):  Verify checkbox status on cell
 *	selectOptionByLabelOnCell(TestObject objTable, int row, int column, String text): Select item by label on cell
 *	selectOptionByIndexOnCell(TestObject objTable, int row, int column, String rangeIndex): Select item by range index on cell
 *	clickOnCell(TestObject objTable, int row, int column): Click on cell
 *  verifyItemExistingOnColumnByAttribute(TestObject objTable, int column, String attributeName, String expectedValue, int iTimeOut, Boolean existing): Verify item existing on column by attribute
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
    private int WAIT_TIME = 5;
    public void setWaitTime(int timeOut){
        WAIT_TIME = timeOut;
    }

    private void verifyValues(String actualValue, String expectedValue) {
        if (actualValue.equals(expectedValue))
            KeywordUtil.markPassed(String.format("Actual object '%s' and expected object '%s' are equal", actualValue, expectedValue));
        else
            KeywordUtil.markFailedAndStop(String.format("Actual object '%s' and expected object '%s' are not equal", actualValue, expectedValue));
    }

    ////////////////////////// General /////////////////////////
    public boolean waitForPresent(String strXpath, int iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), iTimeOut);
            WebElement a = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
            if(a.isDisplayed()) return true;
            else return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /******************************************************
     * Generate dynamic object by XPath
     * @param xpath : String of xpath to find element
     * @return The Dynamic object
     */
    public TestObject generateTestObject(String xpath){
        TestObject dynamic = new TestObject("dynamic");
        dynamic.addProperty("xpath", ConditionType.EQUALS, xpath);
        return dynamic;
    }

    /******************************************************
     * convert TestObject to WebElement
     * @param to: The object to do
     * @return The WebElement object
     */
    public WebElement convertToWebElement(TestObject to){
        return DriverFactory.getWebDriver().findElement(By.xpath(to.findPropertyValue("xpath", false)));
    }
    public List<WebElement> convertToWebElements(TestObject to){
        return DriverFactory.getWebDriver().findElements(By.xpath(to.findPropertyValue("xpath", false)));
    }
    public WebElement convertXpathToWebElement(String xpath){
        return DriverFactory.getWebDriver().findElement(By.xpath(xpath));
    }

    /******************************************************
     * Verify existing element by XPath
     * @param strXpath : String of xpath to find element
     * @param iTimeOut : The second time out to wait
     * @param existing : true (exist) / false (no exist)
     * @return The Dynamic object
     */
    public void verifyExistingByXpath(String strXpath, int iTimeOut, Boolean existing){
        KeywordLogger.getInstance().logInfo("verify WebElement existing [" + strXpath + "] in second time our [" + iTimeOut + "]");
        Boolean currentStatus = waitForPresent(strXpath, iTimeOut);
        verifyValues(currentStatus.toString(), existing.toString());
    }

    /******************************************************
     * Convert rgb to hex code color
     * @param rgbFormat : The rgb format (Ex: rgb(255,255,255))
     * @return the hex color (Ex: #ffffff)
     */
    private String convertRGBtoHEX(String rgbFormat) {
        String[] arrRGB = rgbFormat.replace("(", ",").replace(")", "").split(",");
        Color c = new Color(Integer.valueOf(arrRGB[1].trim()),Integer.valueOf(arrRGB[2].trim()),Integer.valueOf(arrRGB[3].trim()));
        String hexFormat = "#" + Integer.toHexString( c.getRGB() & 0x0000FFFFFFF);
        return hexFormat.replaceFirst("f", "").toLowerCase();
    }

    /******************************************************
     * Verify Element color
     * @param to : The object to do
     * @param attributeName : the attribute name of element: color; border-left-color; background-color
     * @param expectedColor : the expected color (Ex: #ffffff)
     * @return None
     */
    public void verifyElementColor(TestObject to, String attributeName, String expectedColor){
        String currentColor = convertRGBtoHEX(convertToWebElement(to).getCssValue(attributeName));
        verifyValues(currentColor, expectedColor);
    }

    /******************************************************
     * Click on element by Selenium
     * @param to: The object to do
     * @param clickType: click; doubleClick; rightClick; mouseOver
     * @return None
     */
    public void click(TestObject to, String clickType){
        //Convert TestObject to WebElement
        WebElement webElement = convertToWebElement(to);
        KeywordLogger.getInstance().logInfo(clickType + " on element");
        //Clicking on element
        Actions builder = new Actions(DriverFactory.getWebDriver());
        if (clickType == "doubleClick") {
            builder.moveToElement(webElement).doubleClick().build().perform();
        } else if (clickType == "rightClick") {
            builder.moveToElement(webElement).contextClick().build().perform();
        } else if (clickType == "mouseOver") {
            builder.moveToElement(webElement).build().perform();
        } else {
            builder.moveToElement(webElement).click().perform();
        }
    }
    public void click(TestObject to){
        click(to, "click");
    }

    /******************************************************
     * Click Mouse Position
     * @param to: The object to do
     * @param x: The x position on object
     * @param y: The y position on object
     * @param clickType: click; doubleClick; rightClick; mouseOver
     * @return None
     */
    public void clickMousePosition(TestObject to, int x, int y, String clickType){
        //Convert TestObject to WebElement
        WebElement webElement = convertToWebElement(to);
        KeywordLogger.getInstance().logInfo("clickMousePosition ["+ x + "," + y + "] on element");
        //Clicking on element
        Actions builder = new Actions(DriverFactory.getWebDriver());
        if (clickType == "doubleClick") {
            builder.moveToElement(webElement, x, y).doubleClick().perform();
        } else if (clickType == "rightClick") {
            builder.moveToElement(webElement, x, y).contextClick().perform();
        } else if (clickType == "mouseOver") {
            builder.moveToElement(webElement, x, y).perform();
        } else {
            builder.moveToElement(webElement, x, y).click().perform();
        }
    }
    public void clickMousePosition(TestObject to){
        clickMousePosition(to, 5, 5, "click");
    }

    /******************************************************
     * Click element by java script
     * @param to: The object to do
     * @param clickType: click; doubleClick; rightClick; mouseOver; scroll
     * @return None
     */
    public void clickJS(TestObject to, String clickType){
        //Scroll to element
        WebDriver driver = DriverFactory.getWebDriver();
        WebElement webElement = convertToWebElement(to);
        ((JavascriptExecutor) driver).executeScript("(arguments[0]).scrollIntoView();", webElement);
        KeywordLogger.getInstance().logInfo("clickJS [" + clickType + "] on element");
        //Click on element
        if (clickType == "doubleClick") {
            ((JavascriptExecutor) driver).executeScript("(arguments[0]).dblclick();", webElement);
        } else if (clickType == "rightClick") {
            ((JavascriptExecutor) driver).executeScript("(arguments[0]).contextmenu();", webElement);
        } else if (clickType == "mouseOver") {
            ((JavascriptExecutor) driver).executeScript("(arguments[0]).mouseover();", webElement);
        } else if (clickType == "scroll") {
            ((JavascriptExecutor) driver).executeScript("(arguments[0]).scroll();", webElement);
        } else {
            ((JavascriptExecutor) driver).executeScript("(arguments[0]).click();", webElement);
        }
    }
    public void clickJS(TestObject to){
        clickJS(to, "click");
    }

    /******************************************************
     * Execute java script
     * @param to: The object to do
     * @param method: The method to execute: click(); scrollIntoView()
     * @return None
     */
    public void executeJavaScript(TestObject to, String method){
        WebElement webElement = convertToWebElement(to);
        String _textJS = String.format("arguments[0].%s;", method);
        KeywordLogger.getInstance().logInfo("executeJavaScript [" + _textJS + "] on element");
        ((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript(_textJS, webElement);
    }

    /******************************************************
     * Get attribute by java script
     * @param to: The object to do
     * @param fullPathNode: The full path node to get value (form.classList.value)
     * @return The attribute value
     */
    public String getAttributeByJS(TestObject to, String fullPathNode){
        WebElement webElement = convertToWebElement(to);
        String _textJS = String.format("return arguments[0].%s;", fullPathNode);
        KeywordLogger.getInstance().logInfo("getAttributeByJS [" + _textJS + "] on element");
        return ((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript(_textJS, webElement).toString().trim();
    }

    /******************************************************
     * Verify attribute by java script
     * @param to: The object to do
     * @param fullPathNode: The full path node to get value (options[arguments[1].selectedIndex].text)
     * @param expectedValue: The expected value
     * @return The attribute value
     */
    public void verifyAttributeByJS(TestObject to, String fullPathNode, String expectedValue){
        String currentValue = getAttributeByJS(to, fullPathNode);
        verifyValues(currentValue, expectedValue);
    }

    ////////////////////////// ComboBox /////////////////////////

    public void selectOptionByLabel(TestObject to, String text) {
        Select dropdown = new Select(convertToWebElement(to));
        dropdown.selectByVisibleText(text);
    }

    public void selectOptionByValue(TestObject to, String value) {
        Select dropdown = new Select(convertToWebElement(to));
        dropdown.selectByValue(value);
    }

    public void selectOptionByIndex(TestObject to, int index) {
        Select dropdown = new Select(convertToWebElement(to));
        dropdown.selectByIndex(index);
    }

    ////////////////////////// Date Time /////////////////////////

    /******************************************************
     * Set Date element value
     * @param strName: The label name
     * @param strDay: The number day text: 01;02;....;31
     * @param strMonth: The number month text: 01;02;....;12
     * @param strYear: The number year text: 2015;2016;....;2019
     * @return None
     */
    public void setDate(String strName, String strDay, String strMonth, String strYear){
        KeywordLogger.getInstance().logInfo("setDate [" + strDay + "-" + strMonth + "-" + strYear + "] on element");
        String newXpath = String.format("//*[normalize-space(text())='%s']/following::td[1]/select", strName);
        selectOptionByValue(generateTestObject(newXpath + "[1]"), strDay);
        selectOptionByValue(generateTestObject(newXpath + "[2]"), strMonth);
        selectOptionByValue(generateTestObject(newXpath + "[3]"), strYear);
    }

    /******************************************************
     * Get Date element value
     * @param strName: The label name
     * @return The current date as : DD-MM-YYYY
     */
    public String getDate(String strName){
        String newXpath = String.format("//*[normalize-space(text())='%s']/following::td[1]/select", strName);
        String _currentValue = convertXpathToWebElement(newXpath + "[1]").getAttribute("value");
        _currentValue = _currentValue + "-" + convertXpathToWebElement(newXpath + "[2]").getAttribute("value");
        _currentValue = _currentValue + "-" + convertXpathToWebElement(newXpath + "[3]").getAttribute("value");
        return _currentValue;
    }

    /******************************************************
     * Verify Date element value
     * @param strName: The label name
     * @param expectedDay: The expected number day: 01;02;....;31
     * @param expectedMonth: The expected number month: 01;02;....;12
     * @param expectedYear: The expected number year: 2015;2016;....;2019
     * @return None
     */
    public void verifyDateValue(String strName, String expectedDay, String expectedMonth, String expectedYear){
        String _expectedDate = expectedDay + "-" + expectedMonth + "-" + expectedYear;
        verifyValues(getDate(strName), _expectedDate);
    }

    /******************************************************
     * Set Time element value
     * @param strName: The label name
     * @param strHour: The number hour text: 00;02;....;23
     * @param strMinute: The number minute text: 00;02;....;59
     * @return None
     */
    public void setTime(String strName, String strHour, String strMinute){
        KeywordLogger.getInstance().logInfo("setTime [" + strHour + ":" + strMinute + "] on element");
        String newXpath = String.format("//*[normalize-space(text())='%s']/following::td[1]/select", strName);
        selectOptionByValue(generateTestObject(newXpath + "[1]"), strHour);
        selectOptionByValue(generateTestObject(newXpath + "[2]"), strMinute);
    }

    /******************************************************
     * Get Date element value
     * @param strName: The label name
     * @return The current time as : HH:MM
     */
    public String getTime(String strName){
        String newXpath = String.format("//*[normalize-space(text())='%s']/following::td[1]/select", strName);
        String _currentValue = convertXpathToWebElement(newXpath + "[1]").getAttribute("value");;
        _currentValue = _currentValue + ":" + convertXpathToWebElement(newXpath + "[2]").getAttribute("value");;
        return _currentValue;
    }

    /******************************************************
     * Verify Date element value
     * @param strName: The label name
     * @param expectedHour: The expected hour text: 00;02;....;23
     * @param expectedMinute: The expected minute text: 00;02;....;59
     * @return None
     */
    public void verifyTimeValue(String strName, String expectedHour, String expectedMinute){
        String _expectedTime = expectedHour + ":" + expectedMinute;
        verifyValues(getTime(strName), _expectedTime);
    }

    ////////////////////////// List /////////////////////////
    /******************************************************
     * Get list attribute values on list
     * @param objList: The List object
     * @param attributeName: The attribute name to get
     * @return The list attribute value
     */
    public List<String> getListAttributeValues(TestObject objList, String attributeName){
        List<String> listValues = new ArrayList<String>();
        for (WebElement elementItem: convertToWebElements(objList)) {
            listValues.add(elementItem.getAttribute(attributeName).trim());
        }
        return listValues;
    }

    /******************************************************
     * Verify list attribute values on list
     * @param objList: The List object
     * @param attributeName: The attribute name to get
     * @param expectedListValues: The expected list values
     * @return None
     */
    public void verifyListAttributeValues(TestObject objList, String attributeName, List<String> expectedListValues){
        verifyValues(getListAttributeValues(objList, attributeName).toString(), expectedListValues.toString());
    }

    /******************************************************
     * Find item index on list by attribute
     * @param objList: The List object
     * @param attributeName: The attribute name to find
     * @param attributeValue: The attribute value to find
     * @return The list attribute value
     */
    public int findItemIndexOnListByAttribute(TestObject objList, String attributeName, String attributeValue){
        int _findIndex = 0;
        int _Count = 0;
        for (WebElement elementItem: convertToWebElements(objList)) {
            _Count++;
            if (elementItem.getAttribute(attributeName).trim() == attributeValue){
                _findIndex = _Count;
                break;
            }
        }
        return _findIndex;
    }

    /******************************************************
     * Verify item existing on list
     * @param objList: The List object
     * @param attributeName: The attribute name to get
     * @param expectedValue: The expected value
     * @param iTimeOut : The second time out to wait
     * @param existing: true (exist); false (no exist)
     * @return None
     */
    public void verifyItemExistingOnListByAttribute(TestObject objList, String attributeName, String expectedValue, int iTimeOut, Boolean existing){
        String _AttributeName = attributeName;
        if (attributeName == "." || attributeName == "text()") _AttributeName = "[" + attributeName + "='" + expectedValue + "']";
        else _AttributeName = "[@" + attributeName + "='" + expectedValue + "']";
        String newXpath = objList.findPropertyValue("xpath") + _AttributeName;
        verifyExistingByXpath(newXpath, iTimeOut, existing);
    }

    /******************************************************
     * Select list item by index
     * @param objList: The List Test object
     * @param index: The index to select
     * @return None
     */
    public void selectListItemByIndex(TestObject objList, int index){
        String newXpath = "(" + objList.findPropertyValue("xpath") + ")[" + index + "]";
        KeywordLogger.getInstance().logInfo("selectListItemByIndex [" + newXpath + "]");
        convertXpathToWebElement(newXpath).click();
    }

    /******************************************************
     * Select list item by text
     * @param objList: The List Test object
     * @param text: The text of item to select
     * @return None
     */
    public void selectListItemByText(TestObject objList, String text){
        String newXpath = objList.findPropertyValue("xpath") + "[normalize-space(.)='" + text + "']";
        KeywordLogger.getInstance().logInfo("selectListItemByText [" + newXpath + "]");
        convertXpathToWebElement(newXpath).click();
    }

    /******************************************************
     * Count item on List
     * @param objList: The List Test object
     * @return The total number of items
     */
    public int countItemsOnList(TestObject objList){
        return convertToWebElements(objList).size();
    }

    /******************************************************
     * Get attribute item at index on List
     * @param objList: The List Test object
     * @param attributeName: The attribute name to get
     * @param index: The index to select
     * @return None
     */
    public String getAttributeItemIndexOnList(TestObject objList, String attributeName, int index){
        String newXpath = "(" + objList.findPropertyValue("xpath") + ")[" + index + "]";
        return convertXpathToWebElement(newXpath).getAttribute(attributeName).trim();
    }

    ////////////////////////// Table //////////////////////////
    /******************************************************
     * Count columns number
     * @param objTable: The Table object
     * @return The total number of columns
     */
    public int countColumns(TestObject objTable){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[1]";
        return convertToWebElements(generateTestObject(newXpath)).size();
    }

    /******************************************************
     * Count rows number
     * @param objTable: The Table object
     * @return The total number of rows
     */
    public int countRows(TestObject objTable){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr";
        return convertToWebElements(generateTestObject(newXpath)).size();
    }

    /******************************************************
     * Get list header column values on Table
     * @param objTable: The Table object
     * @return The list header column values
     */
    public List<String> getListHeaderColumns(TestObject objTable){
        String newXpath = objTable.findPropertyValue("xpath") + "/*/tr[1]/th";
        return getListAttributeValues(generateTestObject(newXpath), "textContent");
    }

    /******************************************************
     * Verify header column existing on Table
     * @param objTable: The Table object
     * @param headerName: The header column name
     * @param iTimeOut : The second time out to wait
     * @param existing: true (exist); false (no exist)
     * @return None
     */
    public void verifyHeaderColumnExisting(TestObject objTable, String headerName, int iTimeOut, Boolean existing){
        String newXpath = objTable.findPropertyValue("xpath") + "/*/tr[1]/th[normalize-space(text())='" + headerName + "']";
        verifyExistingByXpath(newXpath, iTimeOut, existing);
    }

    /******************************************************
     * Select header column existing on Table
     * @param objTable: The Table object
     * @param headerName: The header or index number
     * @return None
     */
    public void selectHeaderColumn(TestObject objTable, String headerName){
        String newXpath = objTable.findPropertyValue("xpath") + "/*/tr[1]/th[";
        if(StringUtils.isNumeric(headerName)) newXpath = newXpath + headerName + "]";
        else newXpath = newXpath + "normalize-space(text())='" + headerName + "']";
        KeywordLogger.getInstance().logInfo("selectHeaderColumn [" + newXpath + "]");
        convertXpathToWebElement(newXpath).click();
    }

    /******************************************************
     * Select checkbox on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @param status: The status : true(check)/ false(uncheck)
     * @return None
     */
    public void setCheckboxOnCell(TestObject objTable, int row, int column, Boolean status){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]";
        KeywordLogger.getInstance().logInfo("setCheckboxOnCell [" + status.toString() + "][" + newXpath + "]");
        if (status != convertXpathToWebElement(newXpath).isSelected()) {
            convertXpathToWebElement(newXpath).click();
        }
    }
    public void setCheckboxOnCell(TestObject objTable, int row, int column){
        setCheckboxOnCell(objTable, row, column, true);
    }

    /******************************************************
     * Verify checkbox status on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @param expectedStatus: The expected status : true(check)/ false(uncheck)
     * @return None
     */
    public void verifyCheckboxStatusOnCell(TestObject objTable, int row, int column, Boolean expectedStatus){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]";
        Boolean currentStatus = convertXpathToWebElement(newXpath).isSelected();
        verifyValues(currentStatus.toString(), expectedStatus.toString());
    }
    public void verifyCheckboxStatusOnCell(TestObject objTable, int row, int column){
        verifyCheckboxStatusOnCell(objTable, row, column, true);
    }

    /******************************************************
     * Set text on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @param text: The text to set on cell
     * @return None
     */
    public void setTextOnCell(TestObject objTable, int row, int column, String text){
        KeywordLogger.getInstance().logInfo("setTextOnCell [" + row + "," + column + "] [" + text + "] on element");
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]";
        KeywordLogger.getInstance().logInfo("setTextOnCell [" + text + "][" + newXpath + "]");
        convertXpathToWebElement(newXpath).sendKeys(text);
    }

    /******************************************************
     * Select item by label on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @param text: The text item to select on cell
     * @return None
     */
    public void selectOptionByLabelOnCell(TestObject objTable, int row, int column, String text){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/select";
        KeywordLogger.getInstance().logInfo("selectOptionByLabelOnCell [" + text + "][" + newXpath + "]");
        selectOptionByLabel(generateTestObject(newXpath), text);
    }

    /******************************************************
     * Select item by range index on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @param index: The item index to select on cell
     * @return None
     */
    public void selectOptionByIndexOnCell(TestObject objTable, int row, int column, int index){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/select";
        KeywordLogger.getInstance().logInfo("selectOptionByIndexOnCell [" + index + "][" + newXpath + "]");
        selectOptionByIndex(generateTestObject(newXpath), index);
    }

    /******************************************************
     * Click on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @return The attribute value
     */
    public void clickOnCell(TestObject objTable, int row, int column){
        KeywordLogger.getInstance().logInfo("selectOptionByLabelOnCell [" + row + "," + column + "] on Table");
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]";
        KeywordLogger.getInstance().logInfo("clickOnCell [" + newXpath + "]");
        convertXpathToWebElement(newXpath).click();
    }

    /******************************************************
     * Verify item existing on column by attribute
     * @param objTable: The Table object
     * @param column: The column index on Table
     * @param attributeName: The attribute name
     * @param expectedValue: The expected value
     * @param iTimeOut : The second time out to wait
     * @param existing: true(exist) /false (not exist)
     * @return None
     */
    public void verifyItemExistingOnColumnByAttribute(TestObject objTable, int column, String attributeName, String expectedValue, int iTimeOut, Boolean existing){
        String _AttributeName = attributeName;
        if (attributeName == "." || attributeName == "text()") _AttributeName = "//*[" + attributeName + "='" + expectedValue + "']";
        else _AttributeName = "//*[@" + attributeName + "='" + expectedValue + "']";
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td" + _AttributeName;
        verifyExistingByXpath(newXpath, iTimeOut, existing);
    }

    /******************************************************
     * Get text value on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @return The text value of cell
     */
    public String getTextContentOnCell(TestObject objTable, int row, int column){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]";
        return convertXpathToWebElement(newXpath).getAttribute("textContent").trim();
    }

    /******************************************************
     * Verify text value on column
     * @param objTable: The Table object
     * @param column: The column index on Table
     * @param expectedText: The text on cell
     * @param iTimeOut : The second time out to wait
     * @param existing: true(exist) /false (not exist)
     * @return None
     */
    public void verifyTextContentOnColumn(TestObject objTable, int column, String expectedText, int iTimeOut, Boolean existing){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[normalize-space(text())='" + expectedText + "'][" + column + "]";
        verifyExistingByXpath(newXpath, iTimeOut, existing);
    }

    /******************************************************
     * Verify text value on table
     * @param objTable: The Table object
     * @param expectedText: The text on cell
     * @param iTimeOut : The second time out to wait
     * @param existing: true(exist) /false (not exist)
     * @return None
     */
    public void verifyTextContentOnTable(TestObject objTable, String expectedText, int iTimeOut, Boolean existing){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[normalize-space(text())='" + expectedText + "']";
        verifyExistingByXpath(newXpath, iTimeOut, existing);
    }

    /******************************************************
     * Get attribute value on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Table
     * @param attributeName: The attribute name to get
     * @return The attribute value
     */
    public String getAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr[" + row + "]/td[" + column + "]/*[1]";
        return convertXpathToWebElement(newXpath).getAttribute(attributeName).trim();
    }

    /******************************************************
     * Verify attribute value on cell
     * @param objTable: The Table object
     * @param row: The row index on Table
     * @param column: The column index on Tablee
     * @param attributeName: The attribute name to verify
     * @param expectedValue: The expected attribute value to verify
     * @return None
     */
    public void verifyAttributeValueOnCell(TestObject objTable, int row, int column, String attributeName, String expectedValue){
        String currentValue = getAttributeValueOnCell(objTable, row, column, attributeName);
        verifyValues(currentValue, expectedValue);
    }

    /******************************************************
     * Get list Attribute values at column
     * @param objTable: The Table object
     * @param column: The column index on Table
     * @param attributeName: The attribute name to get
     * @return The list Attribute values on cell
     */
    public List<String> getListAttributeValuesAtColumn(TestObject objTable, int column, String attributeName){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[" + column + "]/*[1]";
        return getListAttributeValues(generateTestObject(newXpath), attributeName);
    }

    /******************************************************
     * Get list text content values at column
     * @param objTable: The Table object
     * @param column: The column index on Table
     * @return The list text content values
     */
    public List<String> getListTextContentAtColumn(TestObject objTable, int column){
        String newXpath = objTable.findPropertyValue("xpath") + "/tbody/tr/td[" + column + "]";
        return getListAttributeValues(generateTestObject(newXpath), "textContent");
    }

    ////////////////////////// Menu //////////////////////////
    /******************************************************
     * Select menu items
     * @param menuNodes: The menu node to select
     * @param delimiter: The delimiter text to slipt menu
     * @return None
     */
    public void selectMenu(String menuNodes, String delimiter){
        KeywordLogger.getInstance().logInfo("selectMenu [" + menuNodes + "]");
        String[] _listItems = menuNodes.split(delimiter);
        String newXpath =  "//a[starts-with(normalize-space(.),'%s')]";
        //Select the first main item
        convertXpathToWebElement(newXpath.replace("%s", _listItems[0])).click();
        //Mouse over on item
        int i;
        for (i=1 ; i<_listItems.length; i++) {
            waitForPresent(newXpath.replace("%s", _listItems[i]), WAIT_TIME);
            click(generateTestObject(newXpath.replace("%s", _listItems[i])), "mouseOver");
        }
        //Select on the latest item
        convertXpathToWebElement(newXpath.replace("%s", _listItems[i-1])).click();
        DriverFactory.getWebDriver().manage().timeouts().pageLoadTimeout(WAIT_TIME * 6, TimeUnit.SECONDS);
    }
    public void selectMenu(String menuNodes){
        selectMenu(menuNodes, ">");
    }
}