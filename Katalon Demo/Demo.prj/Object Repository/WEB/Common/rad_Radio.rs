<?xml version="1.0" encoding="UTF-8"?>
<WebElementEntity>
   <description></description>
   <name>rad_Radio</name>
   <tag></tag>
   <elementGuidId>b8c8fc6c-3c05-4218-83a0-69d07711f0ff</elementGuidId>
   <selectorCollection>
      <entry>
         <key>BASIC</key>
         <value>//td[contains(.,'${label}')]/input[@type='radio'][count(//td[contains(.,'${label}')]/text()[normalize-space(translate(.,substring-after(.,'${label}'),''))='${label}']/preceding-sibling::text()) + 1]</value>
      </entry>
   </selectorCollection>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <webElementProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath1</name>
      <type>Main</type>
      <value>//td[normalize-space(text())='${label}']/following::td[1]/input[@type='radio'][${index}]</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath</name>
      <type>Main</type>
      <value>//td[contains(.,'${label}')]/input[@type='radio'][count(//td[contains(.,'${label}')]/text()[normalize-space(translate(.,substring-after(.,'${label}'),''))='${label}']/preceding-sibling::text()) + 1]</value>
   </webElementProperties>
</WebElementEntity>
