package steps.fe;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import constants.Controls;
import keywords.WebUI;
import utilities.Utility;

public class CarsPage {
    static String btnAirportPickup = "//button[.=' Airport Pickup']";

    @When("^User filter search Cars")
    public void filerSearch(){
        Utility.logInfo("STEP", "User filter search Cars", 1);
        Commons.filerSearch(Utility.testData.get("star"), null, null, Utility.testData.get("carType"), Utility.testData.get("airportPickup") );
    }

    @Then("^Verify green Airport Pickup button for each Cars$")
    public static void verifyGreenAirportPickupButton() {
        Utility.logInfo("STEP", "Verify green Airport Pickup button for each Cars", 1);
        String xpathTableRow = Utility.convertXpath(Controls.table, "bgwhite table table-striped") + "/*/tr";
        int rows = WebUI.countItemsOnList(xpathTableRow, null);
        for(int n=1; n<= rows; n++){
            String newXpath = String.format("%s[%d]%s", xpathTableRow, n, btnAirportPickup);
            WebUI.verifyColorElement(newXpath, null, "background-color", "#5cb85c");
        }
    }
}
