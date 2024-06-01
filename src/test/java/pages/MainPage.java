package pages;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


public class MainPage extends PageBase {

    private static class LocatorOf {
        static final By accountMenuButton  = By.className("account-menu--button");
        static final By loginPageNavButton = By.xpath("//a[@href='/user/login']");
    };

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.artmozi.hu");
        this.title = "Címlap";
    }

    public LoginPage goToLogin() {
        this.waitAndReturnElement(LocatorOf.accountMenuButton).click();
        this.waitAndReturnElement(LocatorOf.loginPageNavButton).click();
        return new LoginPage(this.driver);
    }
}
