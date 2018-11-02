import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

class Tests {
	private String startDate = ''
	private long startTime = 0
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		startDate = CustomKeywords.'pdi.Utilities.getUnique'("yyyy/MM/dd HH:mm:ss")
		startTime = System.currentTimeMillis()
//		println testCaseContext.getTestCaseId()
//		println testCaseContext.getTestCaseVariables()
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		if(GlobalVariable.UploadTestStatus) {
			String testCaseName =  testCaseContext.getTestCaseId().toString().split("/").last()
			String testCaseStatus =  testCaseContext.getTestCaseStatus()
			long millis = System.currentTimeMillis() - startTime
			long seconds = (int)(millis/1000)
			// Convert duration format ("HH:mm:ss.SSS")
			String duration = String.format("%02d:%02d:%02d.%03d", (int)(millis / 3600000), (int)((seconds % 3600) / 60), seconds % 60, millis % 1000)
			WS.callTestCase(findTestCase('Common/API/GoogleSheet/Update result to GoogleSheet'), [('p_TCName') : testCaseName
				, ('p_CurrentBuild') : '345', ('p_StartTime') : startDate, ('p_Duration') : duration, ('p_TCStatus') : testCaseStatus])
		}	
	}

	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
//		println testSuiteContext.getTestSuiteId()
		if(GlobalVariable.ReportGoogleSheet){
			WS.callTestCase(findTestCase('Common/API/GoogleSheet/Insert daily result on GoogleSheet'), [:])
			GlobalVariable.ReportGoogleSheet = false
			GlobalVariable.UploadTestStatus = true
		}
	}

	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}
}