<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Set Value Range</name>
   <tag></tag>
   <elementGuidId>25f4e104-2d3e-4596-b076-f7d9e6408308</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n \&quot;range\&quot;: \&quot;${sheetName}!${range}\&quot;,\n \&quot;values\&quot;: [[${valueRange}]],\n \&quot;majorDimension\&quot;: \&quot;ROWS\&quot;\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>${authorization}</value>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
   </httpHeaderProperties>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>PUT</restRequestMethod>
   <restUrl>https://sheets.googleapis.com/v4/spreadsheets/15rw3-1vVkPqDKTik69Y5-6tUXQMyLhBZL1YDMAEnkT0/values/${sheetName}!${range}?valueInputOption=RAW</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceFunction></soapServiceFunction>
   <variables>
      <defaultValue>'UI-Report-IE'</defaultValue>
      <description></description>
      <id>fd538a01-48eb-49eb-99ec-23535495095a</id>
      <masked>false</masked>
      <name>sheetName</name>
   </variables>
   <variables>
      <defaultValue>'C3'</defaultValue>
      <description></description>
      <id>e3bddae1-eda5-4cac-a231-36ea5892d0a5</id>
      <masked>false</masked>
      <name>range</name>
   </variables>
   <variables>
      <defaultValue>'Hello'</defaultValue>
      <description></description>
      <id>513ac160-797f-4240-bb9a-cef89d16b365</id>
      <masked>false</masked>
      <name>valueRange</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.googleAccessToken</defaultValue>
      <description></description>
      <id>ecf43c2c-d32a-4811-b067-07de894a4855</id>
      <masked>false</masked>
      <name>authorization</name>
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
