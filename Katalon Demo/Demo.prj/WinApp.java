package kms;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.testobject.TestObjectProperty;
import com.kms.katalon.core.util.KeywordUtil;
import autoitx4java.AutoItX;

import java.util.List;

public class WinApp {
    private AutoItX winApp = new AutoItX();
    private String windowProperties = "";
    private String controlProperties = "";
    private void verifyValues(String actualValue, String expectedValue) {
        if (actualValue.equals(expectedValue))
            KeywordUtil.markPassed(String.format("Actual object '%s' and expected object '%s' are equal", actualValue, expectedValue));
        else
            KeywordUtil.markFailedAndStop(String.format("Actual object '%s' and expected object '%s' are not equal", actualValue, expectedValue));
    }

    public AutoItX getWinApp() {
        return winApp;
    }

    private String getListProperties(TestObject obj) {
        String _PropertiesValue = "[";
        for (TestObjectProperty _Properties : obj.getActiveProperties()){
            _PropertiesValue = _PropertiesValue + _Properties.getName().toUpperCase() + ":" + _Properties.getValue() + ";";
        }
        return _PropertiesValue.substring(0, _PropertiesValue.length() - 1) + "]";
    }

    private boolean getControl(TestObject obj){
        if(obj.getParentObject()!=null) {
            windowProperties = windowProperties = getListProperties(obj.getParentObject());
            controlProperties = getListProperties(obj);
            return winApp.controlCommandIsVisible(windowProperties, "", controlProperties);
        }
        else {
            windowProperties = getListProperties(obj);
            controlProperties ="";
            return winApp.winExists(windowProperties);
        }
    }

    //////////////////// Window ///////////////////
    public void winActivate(TestObject obj) {
        if(getControl(obj)) winApp.winActivate(windowProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties);
    }

    public Boolean winWaitActive(TestObject obj, int timeout) {
        if(getControl(obj)) return winApp.winWaitActive(windowProperties, "", timeout);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public Boolean winExists(TestObject obj) {
        getControl(obj);
        return winApp.winExists(windowProperties);
    }

    public void verifyWinExisting(TestObject obj, Boolean existing) {
        Boolean currentWin = winExists(obj);
        verifyValues(currentWin.toString(), existing.toString());
    }

    public Boolean winFindString(TestObject obj, String string) {
        getControl(obj);
        return winApp.winExists(windowProperties, string);
    }

    public void verifyTextOnWin(TestObject obj, String expectedText, Boolean existing) {
        Boolean findText = winFindString(obj, expectedText);
        verifyValues(findText.toString(), existing.toString());
    }

    public String winGetText(TestObject obj) {
        if(getControl(obj)) return winApp.winGetText(windowProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public String winGetTitle(TestObject obj) {
        getControl(obj);
        return winApp.winGetTitle(windowProperties);
    }

    public void verifyWinTitle(TestObject obj, String expectedTitle) {
        String currentTitle = winGetTitle(obj);
        verifyValues(currentTitle, expectedTitle);
    }

    public Boolean winWaitNoActive(TestObject obj, int timeout) {
        if(getControl(obj)) return winApp.winWaitNoActive(windowProperties, "", timeout);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public boolean winWait(TestObject obj, int timeout) {
        getControl(obj);
        return winApp.winWait(windowProperties, "", timeout);
    }

    public Integer winGetPosHeight(TestObject obj) {
        if(getControl(obj)) return winApp.winGetPosHeight(windowProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public Integer winGetPosWidth(TestObject obj) {
        if(getControl(obj)) return winApp.winGetPosWidth(windowProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public Integer winGetPosX(TestObject obj) {
        if(getControl(obj)) return winApp.winGetPosX(windowProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public Integer winGetPosY(TestObject obj) {
        if(getControl(obj)) return winApp.winGetPosY(windowProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public void winClose(TestObject obj) {
        if(getControl(obj)) winApp.winClose(windowProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties);
    }

    public void winKill(TestObject obj) {
        getControl(obj);
        winApp.winKill(windowProperties);
    }

    public Boolean winWaitClose(TestObject obj, int timeout) {
        getControl(obj);
        return winApp.winWaitClose(windowProperties, "", timeout);
    }

    public void winSetState(TestObject obj, String status) {
        if(getControl(obj)) {
            int _flag = 0;
            switch (status.toUpperCase()){
                case "SW_HIDE": _flag = 0;
                    break;
                case "SW_MAXIMIZE": _flag = 3;
                    break;
                case "SW_MINIMIZE ": _flag = 6;
                    break;
                case "SW_RESTORE": _flag = 9;
                    break;
                case "SW_SHOW ": _flag = 5;
                    break;
                case "SW_SHOWDEFAULT": _flag = 10;
                    break;
                case "SW_SHOWMAXIMIZED": _flag = 3;
                    break;
                case "SW_SHOWMINIMIZED": _flag = 2;
                    break;
                case "SW_SHOWMINNOACTIVE": _flag = 7;
                    break;
                case "SW_SHOWNA": _flag = 8;
                    break;
                case "SW_SHOWNOACTIVATE": _flag = 4;
                    break;
                default: _flag = 1; //SW_SHOWNORMAL
                    break;
            }
            winApp.winSetState(windowProperties, "", _flag);
        }
        else  KeywordUtil.markFailedAndStop("Not found " + windowProperties);
    }

    public String[][] winList(TestObject obj) {
        if(getControl(obj)) return winApp.winList(windowProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties);
            return null;
        }
    }

    public void winList1(TestObject obj, int x, int y) {
        if(getControl(obj)) winApp.winMove(windowProperties, "", x, y);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties);
    }

    //////////////////// Menu ///////////////////
    public void winMenuSelectItem(TestObject obj, String item) {
        if(getControl(obj)) winApp.winMenuSelectItem(windowProperties, "", item);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void winMenuSelectItem(TestObject obj, String item, String item2) {
        if(getControl(obj)) winApp.winMenuSelectItem(windowProperties, "", item, item2);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void winMenuSelectItem(TestObject obj, String item, String item2, String item3) {
        if(getControl(obj)) winApp.winMenuSelectItem(windowProperties, "", item, item2, item3);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    //////////////////// Text box ///////////////////
    public void controlSetText(TestObject obj, String text) {
        if(getControl(obj)) winApp.ControlSetText(windowProperties, "", controlProperties, text);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public String controlGetText(TestObject obj) {
        if(getControl(obj)) return winApp.controlGetText(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyControlText(TestObject obj, String expectedText) {
        verifyValues(controlGetText(obj), expectedText);
    }

    //////////////////// Tab ///////////////////
    public String controlCommandCurrentTab(TestObject obj) {
        if(getControl(obj)) return winApp.controlCommandCurrentTab(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyCurrentTab(TestObject obj, String expectedText) {
        verifyValues(controlCommandCurrentTab(obj), expectedText);
    }

    public void controlCommandTabLeft(TestObject obj) {
        if(getControl(obj)) winApp.controlCommandTabLeft(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandTabRight(TestObject obj) {
        if(getControl(obj)) winApp.controlCommandTabRight(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    //////////////////// General ///////////////////
    public String getClipboard(int seconds) { return winApp.clipGet(); }

    public void verifyClipboard(int seconds, String expectedText) {
        verifyValues(getClipboard(seconds), expectedText);
    }

    public void setClipboard(String string) { winApp.clipPut(string); }

    public void delay(int seconds) { winApp.sleep(seconds*1000); }

    public void sendKeys(String string) { winApp.send(string); }

    public void controlSend(TestObject obj, String string) {
        if(getControl(obj)) winApp.controlSend(windowProperties, "", controlProperties, string);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Boolean controlCommandIsVisible(TestObject obj) {
        return getControl(obj);
    }

    public void verifyControlVisible(TestObject obj, Boolean expectedStatus) {
        Boolean isVisible = controlCommandIsVisible(obj);
        verifyValues(isVisible.toString(), expectedStatus.toString());
    }

    public Boolean controlCommandIsEnabled(TestObject obj) {
        getControl(obj);
        return winApp.controlCommandIsEnabled(windowProperties, "", controlProperties);
    }

    public void verifyControlEnabled(TestObject obj, Boolean expectedStatus) {
        Boolean isEnabled = controlCommandIsEnabled(obj);
        verifyValues(isEnabled.toString(), expectedStatus.toString());
    }

    public void controlCommandEditPaste(TestObject obj, String string) {
        if(getControl(obj)) winApp.controlCommandEditPaste(windowProperties, "", controlProperties, string);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlFocus(TestObject obj) {
        if(getControl(obj)) winApp.controlFocus(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlClick(TestObject obj) {
        if(getControl(obj)) winApp.controlClick(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    //	[button] The button to click, "left", "right", "middle", "main", "menu", "primary", "secondary". Default is the left button.
    //	[click] The number of times to click the mouse. Default is 1.
    public void controlClick(TestObject obj, String button, int click) {
        if(getControl(obj)) winApp.controlClick(windowProperties, "", controlProperties, button, click);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlClick(TestObject obj, String button, int click, int x, int y) {
        if(getControl(obj)) winApp.controlClick(windowProperties, "", controlProperties, button, click, x, y);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Integer controlGetPosHeight(TestObject obj) {
        if(getControl(obj)) return winApp.controlGetPosHeight(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public Integer controlGetPosWidth(TestObject obj) {
        if(getControl(obj)) return winApp.controlGetPosWidth(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public Integer controlGetPosX(TestObject obj) {
        if(getControl(obj)) return winApp.controlGetPosX(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public Integer controlGetPosY(TestObject obj) {
        if(getControl(obj)) return winApp.controlGetPosY(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    //////////////////// Check box ///////////////////
    public void controlCommandCheck(TestObject obj) {
        if(getControl(obj)) winApp.controlCommandCheck(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandUncheck(TestObject obj) {
        if(getControl(obj)) winApp.controlCommandUncheck(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Boolean controlCommandIsChecked(TestObject obj) {
        if(getControl(obj)) return winApp.controlCommandIsChecked(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyControlCheck(TestObject obj, Boolean expectedStatus) {
        Boolean isChecked = controlCommandIsChecked(obj);
        verifyValues(isChecked.toString(), expectedStatus.toString());
    }

    //////////////////// List View ///////////////////
    public Integer controlListViewFindItem(TestObject obj, String string, String subitem) {
        if(getControl(obj)) return winApp.controlListViewFindItem(windowProperties, "", controlProperties, string, subitem);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewFindItem(TestObject obj, String string, String subitem, Integer expectedNumber) {
        verifyValues(controlListViewFindItem(obj, string, subitem).toString(), expectedNumber.toString());
    }

    public String controlListViewGetSelected(TestObject obj) {
        if(getControl(obj)) return winApp.controlListViewGetSelected(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewGetSelected(TestObject obj, String expectedText) {
        verifyValues(controlListViewGetSelected(obj), expectedText);
    }

    public Integer controlListViewGetItemCount(TestObject obj) {
        if(getControl(obj)) return winApp.controlListViewGetItemCount(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewItemCount(TestObject obj, Integer expectedNumber) {
        verifyValues(controlListViewGetItemCount(obj).toString(), expectedNumber.toString());
    }

    public String controlListViewGetText(TestObject obj, String string, String subitem) {
        if(getControl(obj)) return winApp.controlListViewGetText(windowProperties, "", controlProperties, string, subitem);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewText(TestObject obj, String string, String subitem, String expectedText) {
        verifyValues(controlListViewGetText(obj, string, subitem), expectedText);
    }

    public Integer controlListViewGetSelectedCount(TestObject obj) {
        if(getControl(obj)) return winApp.controlListViewGetSelectedCount(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewSelectedCount(TestObject obj, Integer expectedNumber) {
        verifyValues(controlListViewGetSelectedCount(obj).toString(), expectedNumber.toString());
    }

    public Boolean controlListViewIsSelected(TestObject obj, String item) {
        if(getControl(obj)) return winApp.controlListViewIsSelected(windowProperties, "", controlProperties, item);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewSelected(TestObject obj, String item, Boolean expectedStatus) {
        Boolean isSelected = controlListViewIsSelected(obj, item);
        verifyValues(isSelected.toString(), expectedStatus.toString());
    }

    public Integer controlListViewGetSubItemCount(TestObject obj) {
        if(getControl(obj)) return winApp.controlListViewGetSubItemCount(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewSubItemCount(TestObject obj, Integer expectedNumber) {
        verifyValues(controlListViewGetSubItemCount(obj).toString(), expectedNumber.toString());
    }

    public void controlListViewSelectAll(TestObject obj, String from) {
        if(getControl(obj)) winApp.controlListViewSelectAll(windowProperties, "", controlProperties, from);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlListViewSelectAll(TestObject obj, String from, String to) {
        if(getControl(obj)) winApp.controlListViewSelectAll(windowProperties, "", controlProperties, from, to);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public String[] controlListViewGetSelectedArray(TestObject obj) {
        if(getControl(obj)) return winApp.controlListViewGetSelectedArray(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyListViewSelectedArray(TestObject obj, List<String> expectedArray) {
        verifyValues(controlListViewGetSelectedArray(obj).toString(), expectedArray.toString());
    }

    public void controlListViewSelectClear(TestObject obj) {
        if(getControl(obj)) winApp.controlListViewSelectClear(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlListViewSelectInvert(TestObject obj) {
        if(getControl(obj)) winApp.controlListViewSelectInvert(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlListViewSelectViewChange(TestObject obj, String view) {
        if(getControl(obj)) winApp.controlListViewSelectViewChange(windowProperties, "", controlProperties, view);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    //////////////////// ListBox or ComboBox///////////////////
    public void controlCommandHideDropDown(TestObject obj, String item) {
        if(getControl(obj)) winApp.controlCommandHideDropDown(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandShowDropdown(TestObject obj, String item) {
        if(getControl(obj)) winApp.controlCommandShowDropdown(windowProperties, "", controlProperties);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandSelectString(TestObject obj, String string) {
        if(getControl(obj)) winApp.controlCommandSelectString(windowProperties, "", controlProperties, string);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandSetCurrentSelection(TestObject obj, String occurance) {
        if(getControl(obj)) winApp.controlCommandSetCurrentSelection(windowProperties, "", controlProperties, occurance);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandAddString(TestObject obj, String string) {
        if(getControl(obj)) winApp.controlCommandAddString(windowProperties, "", controlProperties, string);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlCommandDeleteString(TestObject obj, String string) {
        if(getControl(obj)) winApp.controlCommandDeleteString(windowProperties, "", controlProperties, string);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Integer controlCommandFindString(TestObject obj, String string) {
        if(getControl(obj)) return winApp.controlCommandFindString(windowProperties, "", controlProperties, string);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyControlFindString(TestObject obj, String string, Integer expectedNumber) {
        verifyValues(controlCommandFindString(obj, string).toString(), expectedNumber.toString());
    }

    public String controlCommandGetCurrentSelection(TestObject obj, int charLength) {
        if(getControl(obj)) return winApp.controlCommandGetCurrentSelection(windowProperties, "", controlProperties, charLength);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }


    //////////////////// Tree View ///////////////////
    public void controlTreeViewCheck(TestObject obj, String item) {
        if(getControl(obj)) winApp.controlTreeViewCheck(windowProperties, "", controlProperties, item);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlTreeViewCollapse(TestObject obj, String item) {
        if(getControl(obj)) winApp.controlTreeViewCollapse(windowProperties, "", controlProperties, item);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Boolean controlTreeViewExists(TestObject obj, String item) {
        if(getControl(obj)) return winApp.controlTreeViewExists(windowProperties, "", controlProperties, item);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyTreeViewExists(TestObject obj, String item, Boolean expectedStatus) {
        Boolean isExisted = controlTreeViewExists(obj, item);
        verifyValues(isExisted.toString(), expectedStatus.toString());
    }

    public void controlTreeViewExpand(TestObject obj, String item) {
        if(getControl(obj))  winApp.controlTreeViewExpand(windowProperties, "", controlProperties, item);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Integer controlTreeViewGetItemCount(TestObject obj, String item) {
        if(getControl(obj)) return winApp.controlTreeViewGetItemCount(windowProperties, "", controlProperties, item);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyTreeViewItemCount(TestObject obj, String item, Integer expectedNumber) {
        verifyValues(controlTreeViewGetItemCount(obj, item).toString(), expectedNumber.toString());
    }

    public String controlTreeViewGetSelectedItemText(TestObject obj) {
        if(getControl(obj)) return winApp.controlTreeViewGetSelectedItemText(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyTreeViewSelectedItemText(TestObject obj, String expectedText) {
        verifyValues(controlTreeViewGetSelectedItemText(obj), expectedText);
    }

    public Integer controlTreeViewGetSelectedItemIndex(TestObject obj) {
        if(getControl(obj)) return winApp.controlTreeViewGetSelectedItemIndex(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyTreeViewSelectedItemIndex(TestObject obj, Integer expectedNumber) {
        verifyValues(controlTreeViewGetSelectedItemIndex(obj).toString(), expectedNumber.toString());
    }

    public String controlTreeViewGetText(TestObject obj, String item) {
        if(getControl(obj)) return winApp.controlTreeViewGetText(windowProperties, "", controlProperties, item);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyTreeViewText(TestObject obj, String item, String expectedText) {
        verifyValues(controlTreeViewGetText(obj, item), expectedText);
    }

    public void controlTreeViewSelect(TestObject obj, String item) {
        if(getControl(obj)) winApp.controlTreeViewSelect(windowProperties, "", controlProperties, item);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public void controlTreeViewUncheck(TestObject obj, String item) {
        if(getControl(obj)) winApp.controlTreeViewUncheck(windowProperties, "", controlProperties, item);
        else KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
    }

    public Integer controlTreeViewIsChecked(TestObject obj, String item) {
        if(getControl(obj)) return winApp.controlTreeViewIsChecked(windowProperties, "", controlProperties);
        else {
            KeywordUtil.markFailedAndStop("Not found " + windowProperties + "-" + controlProperties);
            return null;
        }
    }

    public void verifyTreeViewChecked(TestObject obj, String item, Integer expectedNumber) {
        verifyValues(controlTreeViewIsChecked(obj, item).toString(), expectedNumber.toString());
    }

    public void uploadFile(String filePath) {
        winApp.sleep(3000);
        winApp.winWaitActive("[CLASS:#32770]");
        winApp.winActivate("[CLASS:#32770]");
        winApp.ControlSetText("[CLASS:#32770]", "", "Edit1", filePath);
        winApp.controlClick("[CLASS:#32770]", "", "Button1");
        winApp.sleep(3000);
    }
}