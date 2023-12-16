package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public abstract class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    protected void waitElementToBeClickable(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException timeoutException) {
            Assert.fail("Test failed because element " + elementName + " is not clickable!");
        }
    }

    protected void waitVisibilityOfElement(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException timeoutException) {
            Assert.fail("Test failed because element " + elementName + " is not visible!");
        }
    }

    public void waitForLoad(long waitTime) {
        ExpectedCondition<Boolean> expectation = driver -> {
            assert driver != null;
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        };
        try {
            new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(expectation);
        } catch (Throwable ignored) {
        }
    }

    public void waitSpecificTimeToBeTrue(Integer time, Boolean condition) {
        await("Wait condition to be true")
                .atMost(time, TimeUnit.SECONDS).until(() -> condition);
    }

    public void waitToBeTrue(Boolean condition) {
        waitSpecificTimeToBeTrue(5, condition);
    }

    public void waitUntilPageIsLoaded(String pagePath) {
        wait.until(ExpectedConditions.urlContains(pagePath));
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void performClick(WebElement element, String elementName) {
        waitElementToBeClickable(element, elementName);
        element.click();
    }

    public void performJSClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }


    public void performScrollDown(WebElement scrollableElement, int waitBeforeNextScroll, int scrollTimes) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int i = 1;
        while (i <= scrollTimes) {
            jse.executeScript("arguments[0].scrollBy(0,document.body.scrollHeight)", scrollableElement, waitBeforeNextScroll, scrollTimes);
            waitForLoad(waitBeforeNextScroll);
            i++;
        }
    }

    public static void performMovingElementIntoMiddleOfView(WebDriver driver, WebElement element) throws InterruptedException {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
        Thread.sleep(300);
    }


    public void performClear(WebElement element) {
        element.sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
    }

    public void performInput(WebElement element, String elementName, String inputText) {
        waitVisibilityOfElement(element, elementName);
        element.clear();
        element.sendKeys(inputText);
    }

    public void performMouseHover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
    }

    public void uploadFileOSSide(String filePath) throws AWTException {
        //put path to your image in a clipboard
        StringSelection ss = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        //imitate mouse events like ENTER, CTRL+C, CTRL+V
        Robot robot = new Robot();
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(90);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public String rgbaToHexColor(WebElement element) {
        String color = element.getCssValue("color");
        String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
        hexValue[0] = hexValue[0].trim();
        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);
        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
        return actualColor.toUpperCase();
    }

    protected void activateHandler() {
        String lastWindowHandle = new TreeSet<>(driver.getWindowHandles()).last();
        driver.switchTo().window(lastWindowHandle);
    }
}
