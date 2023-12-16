package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.SearchPage;

public class SearchTestStepDefinitions extends BaseUiSteps {


    public SearchTestStepDefinitions(UiScenarioContext uiScenarioContext) {
        super(uiScenarioContext);
    }

    @Given("print Given")
    public void print_given() {
        driver.get("https://www.expedia.com/");
    }

    @When("print When")
    public void print_when() throws Exception {
        SearchPage.navigateToFlightsTab(driver);
        SearchPage.fillOriginTextBox(driver, "New York");
        SearchPage.fillDestinationTextBox(driver, "Chicago");
        SearchPage.fillDepartureDateTextBox(driver, "12/25/2015");
        SearchPage.fillReturnDateTextBox(driver, "12/31/2015");
    }

    @Then("print Then")
    public void print_then() {
        // Write code here that turns the phrase above into concrete actions
    }
}
