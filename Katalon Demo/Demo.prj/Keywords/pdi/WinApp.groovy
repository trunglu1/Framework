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

	private getControl(TestObject to){
		if(to.getParentObject() != null) {
			windowProperties = getListPropeties(to.getParentObject())
			controlProperties = getListPropeties(to)
		}
		else {
			windowProperties = getListPropeties(to)
			controlProperties =''
		}
	}

	@Keyword
	def focusWindow(TestObject to) {
		getControl(to)
		winApp.winActivate(windowProperties)
		KeywordLogger.getInstance().logInfo("focusWindow on window " + windowProperties)
	}

	@Keyword
	def click(TestObject to) {
		getControl(to)
		if(winApp.controlClick(windowProperties, '', controlProperties)){
			KeywordLogger.getInstance().logInfo("click on " + windowProperties + "-" + controlProperties)
		}
		else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties)
	}

	@Keyword
	def setText(TestObject to, String text) {
		getControl(to)
		if(winApp.ControlSetText(windowProperties, '', controlProperties, text)){
			KeywordLogger.getInstance().logInfo("Text '" + text + "' is setText on " + windowProperties + "-" + controlProperties)
		}
		else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties)
	}

	@Keyword
	def getText(TestObject to) {
		getControl(to)
		return winApp.controlGetText(windowProperties, '', controlProperties)
	}

	@Keyword
	def verifyText(TestObject to, String expectedText) {
		getControl(to)
		String currentText = winApp.controlGetText(windowProperties, '', controlProperties)
		WebUI.verifyEqual(currentText, expectedText)
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
