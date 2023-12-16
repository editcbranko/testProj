package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static pages.annotations.AnnotationProcessor.annotatePOM;

public class PagesContainer {
    WebDriver driver;
    WebDriverWait wait;

    private SearchPage searchPage;

    public PagesContainer(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public SearchPage getLoginPage() {
        return (searchPage == null) ? searchPage = annotatePOM(new SearchPage(driver, wait)) : searchPage;
    }
}
