package pdi

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import autoitx4java.AutoItX

public class WinApp {
	//https://uploadfiles.io/
	//	https://stackoverflow.com/questions/49559050/issue-in-accessing-jacob-1-18-x86-dll-at-run-time
	//	Copy jacob-1.19-x64.dll to inside folders :/Java/jdk/jre/bin and /Java/jdk/jre/lib/ext
	private AutoItX winApp = new AutoItX()
	private String windowProperties = ''
	private String controlProperties = ''

	private String getListPropeties(TestObject to){
		String _PropertiesValue = "["
		for (def _Properties in to.getActiveProperties()){
			_PropertiesValue = _PropertiesValue + _Properties.name.toUpperCase() + ":" + _Properties.value + ";"
		}
		return _PropertiesValue.substring(0, _PropertiesValue.size() -1) + "]"
	}

	private Boolean getControl(TestObject to){
		if(to.getParentObject() != null) {
			windowProperties = getListPropeties(to.getParentObject())
			controlProperties = getListPropeties(to)
			return winApp.controlCommandIsVisible(windowProperties, "", controlProperties)
		}
		else {
			windowProperties = getListPropeties(to)
			controlProperties =''
			return winApp.winExists(windowProperties);
		}
	}

	@Keyword
	def winActivate(TestObject to) {
		if (getControl(to)) winApp.winActivate(windowProperties)
		else KeywordUtil.markFailedAndStop("Not found " + windowProperties)
	}

	@Keyword
	def controlClick(TestObject to) {
		if (getControl(to)) winApp.controlClick(windowProperties, '', controlProperties)
		else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties)
	}

	@Keyword
	def controlSetText(TestObject to, String text) {
		if (getControl(to)) winApp.ControlSetText(windowProperties, '', controlProperties, text)
		else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties)
	}

	@Keyword
	def controlGetText(TestObject to) {
		if (getControl(to)) return winApp.controlGetText(windowProperties, '', controlProperties)
		else {
			KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties)
			return null
		}
	}

	@Keyword
	def verifyText(TestObject to, String expectedText) {
		String currentText = controlGetText(to)
		WebUI.verifyEqual(currentText, expectedText)
	}

	@Keyword
	def sendKeys(String expectedText) {
		winApp.send(expectedText)
	}

	@Keyword
	def uploadFile(String filePath) {
		winApp.sleep(3000)
		winApp.winWaitActive("[CLASS:#32770]")
		winApp.winActivate("[CLASS:#32770]")
		winApp.ControlSetText("[CLASS:#32770]", "", "Edit1", filePath)
		winApp.controlClick("[CLASS:#32770]", "", "Button1")
		winApp.sleep(2000)
	}
}
