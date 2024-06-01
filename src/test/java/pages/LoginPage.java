package pages;
import exceptions.InvalidCredentialsException;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;

public class LoginPage extends PageBase {

    private static class LocatorOf {
        static By usernameInput = By.xpath("//form[@class='user-login-form']//input[@id='edit-name']");
        static By passwordInput = By.xpath("//form[@class='user-login-form']//input[@id='edit-pass']");
        static By loginButton   = By.xpath("//form[@class='user-login-form']//button[@id='edit-submit']");
    };

    public LoginPage(WebDriver driver) {
        super(driver);
        this.title = "Bejelentkezés";
    }

    public ProfilePage login(String username, String password) throws InvalidCredentialsException {
        this.waitAndReturnElement(LocatorOf.usernameInput).sendKeys(username);
        this.waitAndReturnElement(LocatorOf.passwordInput).sendKeys(password);
        this.waitAndReturnElement(LocatorOf.loginButton).click();
        if (this.failedLogin()) {
            throw new InvalidCredentialsException();
        }
        return new ProfilePage(this.driver, username);
    }

    private Boolean failedLogin() {
        return this.getBodyText().contains("Ismeretlen felhasználó vagy hibás jelszó.");
    }
}
