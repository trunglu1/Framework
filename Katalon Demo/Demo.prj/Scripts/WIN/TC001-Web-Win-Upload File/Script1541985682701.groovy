import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.internal.PathUtil

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.phptravels.net/admin')

WebUI.maximizeWindow()

WebUI.setText(findTestObject('Travels/Page_Administator Login/input_Login Panel_email'), 'admin@phptravels.com')

WebUI.setEncryptedText(findTestObject('Travels/Page_Administator Login/input_Login Panel_password'), 'orSGNCvhf+w8SKCExcig5g==')

//WebUI.click(findTestObject('Travels/Page_Administator Login/button_Login'))
CustomKeywords.'pdi.Elements.clickJS'(findTestObject('Travels/Page_Administator Login/button_Login'))
WebUI.waitForPageLoad(30)
WebUI.click(findTestObject('Travels/Page_Dashboard/mnu_Main menu', ['MainMenu' : 'Hotels']))

WebUI.click(findTestObject('Travels/Page_Dashboard/mnu_Sub menu', ['SubMenu' :  'Hotels']))

WebUI.click(findTestObject('Travels/Page_Hotels Management/a_Upload (13)'))
WebUI.waitForPageLoad(30)
WebUI.click(findTestObject('Travels/Page_Hotel Gallery/a_Add Photos'))
WebUI.waitForPageLoad(30)
// Katalon issue on IE & Edge 
//WebUI.click(findTestObject('Travels/Page_Hotel Gallery/span_Gallery Management_drop'))
CustomKeywords.'pdi.Elements.clickSelenium'(findTestObject('Travels/Page_Hotel Gallery/span_Gallery Management_drop'))

_PathFile = PathUtil.relativeToAbsolutePath("Data Files/WIN/P2.JPG", RunConfiguration.getProjectDir())
CustomKeywords.'pdi.WinApp.uploadFile'(_PathFile)

WebUI.delay(5)

WebUI.closeBrowser()



