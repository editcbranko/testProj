package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DriverUtils;
import pages.PagesContainer;

public class UiScenarioContext {
    public WebDriver driver;
    public WebDriverWait wait;

    public DriverUtils driverUtils;
    public PagesContainer pagesContainer;
}

