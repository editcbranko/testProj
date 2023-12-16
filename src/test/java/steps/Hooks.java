package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DriverUtils;
import pages.PagesContainer;

import java.net.MalformedURLException;
import java.time.Duration;

@Slf4j
public class Hooks {
    UiScenarioContext uiScenarioContext;

    public Hooks(UiScenarioContext uiScenarioContext) {
        this.uiScenarioContext = uiScenarioContext;
    }

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        log.info("Scenario: " + scenario.getName() + " started");
    }

    @Before(value = "@ui", order = 1)
    public void beforeUiScenario() throws MalformedURLException {
        uiScenarioContext.driver = BaseDriver.initializeDriver("chrome");
        uiScenarioContext.wait = new WebDriverWait(uiScenarioContext.driver, Duration.ofSeconds(5));
        uiScenarioContext.driverUtils = new DriverUtils(uiScenarioContext.driver, uiScenarioContext.wait);
        uiScenarioContext.pagesContainer = new PagesContainer(uiScenarioContext.driver, uiScenarioContext.wait);
    }

    @After(value = "@ui", order = 1)
    public void afterUiScenario(Scenario scenario) throws Exception {
        if (scenario.isFailed() && uiScenarioContext.driver != null) {
            uiScenarioContext.driverUtils.takeScreenshot(scenario.getName());
            uiScenarioContext.driverUtils.addScreenshotAllure(scenario.getName());
        }
        if (uiScenarioContext.driver != null) {
            uiScenarioContext.driver.manage().deleteAllCookies();
            uiScenarioContext.driver.quit();
        }
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: " + scenario.getName() + " finished");
    }
}
