import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

_listItems = p_MenuItems.split('>')

'Select the first main item'
WebUI.click(findTestObject('WEB/Dynamic/dynamic_Id', [('id') : _listItems[0]]))

'Mouse over on item'
for (i=1 ; i<_listItems.size(); i++) {
	WebUI.waitForElementVisible(findTestObject('WEB/Main/mnu_Item', [('itemName') : _listItems[i]]), GlobalVariable.MediumTime)
	WebUI.mouseOver(findTestObject('WEB/Main/mnu_Item', [('itemName') : _listItems[i]]))
}

'Select on the latest item'
WebUI.click(findTestObject('WEB/Main/mnu_Item', [('itemName') : _listItems[i-1]]))

WebUI.waitForPageLoad(GlobalVariable.LongTime)