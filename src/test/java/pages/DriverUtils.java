package pages;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DriverUtils {
    WebDriver driver;
    WebDriverWait wait;
    private final String screenshotPath = "allure-results/";

    public DriverUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void takeScreenshot(String scenarioName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(screenshotPath + scenarioName + ".png"));
    }

    public void addScreenshotAllure(String scenarioName) throws IOException {
        Path content = Paths.get(screenshotPath + scenarioName + ".png");
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(scenarioName, is);
        }
    }

    public String getItemFromLocalStorage(String key) {
        return (String) ((JavascriptExecutor) driver).executeScript(String.format(
                "return localStorage.getItem('%s');", key));
    }

    public void addTokenToLocalStorage(String rcAuthValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem(arguments[0],arguments[1])", "rc_auth", rcAuthValue);
        LocalStorage local = ((WebStorage) driver).getLocalStorage();

        local.setItem("rc_auth", rcAuthValue);
        local.setItem("organizationId", "1");

        System.out.println("Local storage: " + local.getItem("rc_auth"));
    }
}