import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

GlobalVariable.IssueInfo = "#12345"
WebUI.delay(4)
KeywordUtil.markFailed("FAILED test case")