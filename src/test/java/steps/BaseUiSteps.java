package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import pages.DriverUtils;
import pages.PagesContainer;
import pages.SearchPage;

public abstract class BaseUiSteps {
    protected UiScenarioContext uiScenarioContext;
    protected SoftAssert assertions;


    protected DriverUtils driverUtils;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected PagesContainer pagesContainer;
    protected SearchPage searchPage;

    public BaseUiSteps(UiScenarioContext uiScenarioContext) {
        this.uiScenarioContext = uiScenarioContext;
        this.assertions = new SoftAssert();

        this.driver = uiScenarioContext.driver;
        this.wait = uiScenarioContext.wait;
        this.driverUtils = uiScenarioContext.driverUtils;
        this.pagesContainer = uiScenarioContext.pagesContainer;
    }
}
