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
	private String windowTitle = ''
	private String controlTitle = ''

	private getControl(TestObject to){
		if(to.getParentObject()!=null) {
			windowTitle = to.getParentObject().findPropertyValue('title')
			controlTitle = to.findPropertyValue('title')
		}
		else {
			windowTitle = to.findPropertyValue('title')
			controlTitle =''
		}
	}

	@Keyword
	def focusWindow(TestObject to) {
		getControl(to)
		winApp.winActivate(windowTitle)
		KeywordLogger.getInstance().logInfo("focusWindow on window [" + windowTitle + "]")
	}

	@Keyword
	def click(TestObject to) {
		getControl(to)
		if(winApp.controlClick(windowTitle, '', controlTitle)){
			KeywordLogger.getInstance().logInfo("click on [" + windowTitle + "][" + controlTitle + "]")
		}
		else KeywordUtil.markFailedAndStop("Not found [" + windowTitle + "][" + controlTitle + "]")
	}

	@Keyword
	def setText(TestObject to, String text) {
		getControl(to)
		if(winApp.ControlSetText(windowTitle, '', controlTitle, text)){
			KeywordLogger.getInstance().logInfo("Text '" + text + "' is setText on [" + windowTitle + "][" + controlTitle + "]")
		}
		else KeywordUtil.markFailedAndStop("Not found [" + windowTitle + "][" + controlTitle + "]")
	}

	@Keyword
	def getText(TestObject to) {
		getControl(to)
		return winApp.controlGetText(windowTitle, '', controlTitle)
	}

	@Keyword
	def verifyText(TestObject to, String expectedText) {
		getControl(to)
		String currentText = winApp.controlGetText(windowTitle, '', controlTitle)
		WebUI.verifyEqual(currentText, expectedText)
	}

	@Keyword
	def uploadFile(String filePath) {
		winApp.sleep(3000)
		winApp.winWaitActive("[REGEXPTITLE:.*; CLASS:#32770]")
		winApp.winActivate("[REGEXPTITLE:.*; CLASS:#32770]")
		winApp.ControlSetText("[REGEXPTITLE:.*; CLASS:#32770]", "", "Edit1", filePath)
		winApp.controlClick("[REGEXPTITLE:.*; CLASS:#32770]", "", "Button1")
		winApp.sleep(2000)
	}
}
