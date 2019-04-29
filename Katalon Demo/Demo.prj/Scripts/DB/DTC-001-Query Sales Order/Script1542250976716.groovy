import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

a = CustomKeywords.'pdi.Database.getDataRow'("select empid,shipaddress,freight from Sales.Orders where custid = 71", 4)
WebUI.comment(a['freight'])

b = CustomKeywords.'pdi.Database.getCellValue'("select empid,shipaddress,freight from Sales.Orders where custid = 71", 3, 4)
WebUI.comment(b)

c = CustomKeywords.'pdi.Database.getCellValue'("select empid,shipaddress,freight from Sales.Orders where custid = 71", 'freight', 4)
WebUI.comment(c)
