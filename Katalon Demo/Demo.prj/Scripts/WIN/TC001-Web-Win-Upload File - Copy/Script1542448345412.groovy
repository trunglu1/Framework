import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.internal.PathUtil

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.google.com.vn/')
WebUI.waitForPageLoad(30)
findTestObject('Travels/google')
//WebUI.setText(findTestObject('Travels/Page_Administator Login/input_Login Panel_email'), 'admin@phptravels.com')
//
//WebUI.setEncryptedText(findTestObject('Travels/Page_Administator Login/input_Login Panel_password'), 'orSGNCvhf+w8SKCExcig5g==')

////WebUI.click(findTestObject('Travels/Page_Administator Login/button_Login'))
//CustomKeywords.'pdi.WebElements.verifyElementColor'(findTestObject('Travels/Page_Administator Login/button_Login'), 'background-color', '#0031bc')

a = CustomKeywords.'pdi.WebElements.getAttributeByJS'(findTestObject('Travels/google'), "outerHTML")

WebUI.comment(a)