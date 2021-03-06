<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>GET_Get list test cases</name>
   <tag></tag>
   <elementGuidId>aaaf9559-f25d-4e96-9558-c76873652605</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n \&quot;range\&quot;: \&quot;${sheetName}!${range}\&quot;,\n \&quot;values\&quot;: [[\&quot;${valueRange}\&quot;]],\n \&quot;majorDimension\&quot;: \&quot;ROWS\&quot;\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>${p_Authorization}</value>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
   </httpHeaderProperties>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>https://sheets.googleapis.com/v4/spreadsheets/${p_Spreadsheet_Id}/values/${p_SheetName}!A:A</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceFunction></soapServiceFunction>
   <variables>
      <defaultValue>'15rw3-1vVkPqDKTik69Y5-6tUXQMyLhBZL1YDMAEnkT0'</defaultValue>
      <description></description>
      <id>3eb4726d-9c0b-474f-a712-82f7a9dd6faa</id>
      <masked>false</masked>
      <name>p_Spreadsheet_Id</name>
   </variables>
   <variables>
      <defaultValue>'UI-Report-Chrome'</defaultValue>
      <description></description>
      <id>fd538a01-48eb-49eb-99ec-23535495095a</id>
      <masked>false</masked>
      <name>p_SheetName</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>ecf43c2c-d32a-4811-b067-07de894a4855</id>
      <masked>false</masked>
      <name>p_Authorization</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
