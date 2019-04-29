import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

newQSL1 = "select empid,shipaddress,freight from Sales.Orders where custid = 70"
newQSL2 = "select empid,shipaddress,freight from Sales.Orders where custid = 71"

a = CustomKeywords.'pdi.Database.getDataColumn'(newQSL1, 'freight')
WebUI.comment(a.toString())

b = CustomKeywords.'pdi.Database.getDataColumn'(newQSL2, 3)
WebUI.comment(b.toString())