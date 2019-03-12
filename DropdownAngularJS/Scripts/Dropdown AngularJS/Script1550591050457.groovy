import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

'Open AngularJS page that tagname of dropdown is md-select'
WebUI.openBrowser('https://material.angularjs.org/latest/demo/select')
WebUI.maximizeWindow()

'Select All items: Select Header section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectAllOption'(findTestObject('Md-Option/cbo_CheckboxOptionHeader'), true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionHeader'), "Corn, Onions, Kale, Arugula, Peas, Zucchini")

'Select Checkbox items by Index from 2 to 5: Option Groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByIndex'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "2-5", true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Sausage, Ground Beef, Bacon, Mushrooms")

'Deselect Checkbox items by Index : 3 and 4: Option Groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByIndex'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "3,4", false)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Sausage, Mushrooms")

'Deselect Checkbox item by Index at 5: Option Groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByIndex'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), 5, false)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Sausage")

'Deselect Checkbox items by Name: Kale,Peas: Select Header section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByName'(findTestObject('Md-Option/cbo_CheckboxOptionHeader'), "Kale,Peas", false)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionHeader'), "Corn, Onions, Arugula, Zucchini")

'Select items by Index at 4: Basic Usage/Account Preferences section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByIndex'(findTestObject('Md-Option/cbo_State'), 4)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_State'), "AZ")

'Select items by Name: Plate: Basic Usage/Battle Preferences section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByName'(findTestObject('Md-Option/cbo_Armor'), "Plate")
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_Armor'), "Plate")

'Select items by Name: Item 2: Selected Text section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByName'(findTestObject('Md-Option/cbo_SelectedItem'), "Item 2")
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_SelectedItem'), "You have selected: Item 2")

'Select items by Name: Fred Jones: Options With Async Search section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByName'(findTestObject('Md-Option/cbo_LoadingIndicator'), "Fred Jones")
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_LoadingIndicator'), "Fred Jones")

'Select items by Index at 2: Options With Async Search section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByIndex'(findTestObject('Md-Option/cbo_LoadingIndicator'), 2)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_LoadingIndicator'), "Shaggy Rodgers")

'Select Checkbox items by Name: Bacon under Meats: Option Groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectSubOptionByName'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Meats", "Bacon", true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Sausage, Bacon")

'Verify AK item is disabled: Basic Usage/Account Preferences section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionItemsStatus'(findTestObject('Md-Option/cbo_State'), "AK", false)

'Verify AZ,CO items are enabled: Basic Usage/Account Preferences section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionItemsStatus'(findTestObject('Md-Option/cbo_State'), "AZ,CO", true)

'Select Checkbox items by Index: 2 and 4 under Veggies: Option Groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectSubOptionByIndex'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Veggies", "2,4", true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Md-Option/cbo_CheckboxOptionGroup'), "Sausage, Bacon, Onion, Green Olives")

'Open Other AngularJS page that tagname of dropdown is mat-select'
WebUI.navigateToUrl('https://material.angular.io/components/select/overview')
WebUI.click(findTestObject('Mat-Option/lnk_Examples'))
WebUI.maximizeWindow()

'Select items by Index at 3: Select with form field features section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByIndex'(findTestObject('Mat-Option/cbo_FavoriteAnimal'), 3)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Mat-Option/cbo_FavoriteAnimal'), "Cat")

'Verify Option 2 item is disabled: Disabled select section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionItemsStatus'(findTestObject('Mat-Option/cbo_DisabledSelect'), "Option 2 (disabled)", false)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionItemsStatus'(findTestObject('Mat-Option/cbo_DisabledSelect'), "Option 1,Option 3", true)

'Select Checkbox items by Name: Select with multiple selection section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectOptionByName'(findTestObject('Mat-Option/cbo_MultipleSelection'), "Mushroom,Tomato", true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Mat-Option/cbo_MultipleSelection'), "Mushroom, Tomato")

'Select items by Name: Oddish under Grass: Select with option groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectSubOptionByName'(findTestObject('Mat-Option/cbo_OptionGroup'), "Grass", "Oddish", true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Mat-Option/cbo_OptionGroup'), "Oddish")

'Select items by Index: at 2 under Psychic: Select with option groups section'
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.selectSubOptionByIndex'(findTestObject('Mat-Option/cbo_OptionGroup'), "Psychic", 2, true)
CustomKeywords.'com.katalon.plugin.keyword.angularjs.DropdownKeywords.verifyOptionSelectedByName'(findTestObject('Mat-Option/cbo_OptionGroup'), "Mewtwo")

'Close Browser'
WebUI.closeBrowser()