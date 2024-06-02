package pages;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;

public class LoginPage extends PageBase {

    private static class LocatorOf {
        static By statusIcon        = By.xpath("//div[@class='account--status']");
        static By usernameInput     = By.xpath("//form[@class='user-login-form']//input[@id='edit-name']");
        static By passwordInput     = By.xpath("//form[@class='user-login-form']//input[@id='edit-pass']");
        static By accountMenuButton = By.xpath("//nav[@role='navigation']//a[@id='account-menu']");
        static By logoutButton      = By.xpath("//ul[contains(@class,'menu--account')]//li//a[@href='/user/logout']");
    };

    public LoginPage(WebDriver driver) {
        super(driver, "https://artmozi.hu/user/login");
    }

    @Override
    protected String expectedTitle() {
        return "Bejelentkezés";
    }

    public void login(String username, String password) {
        WebElement usernameInput = this.waitAndReturnElement(LocatorOf.usernameInput);
        WebElement passwordInput = this.waitAndReturnElement(LocatorOf.passwordInput);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        passwordInput.submit();
    }

    public void logout() {
        this.waitAndReturnElement(LocatorOf.accountMenuButton).click();
        this.waitAndReturnElement(LocatorOf.logoutButton).click();
    }

    public String getStatusColor() {
        WebElement statusIcon = this.waitAndReturnElement(LocatorOf.statusIcon);
        return statusIcon.getCssValue("background-color");
    }

    public Boolean isLoggedIn() {
        WebElement statusIcon = this.waitAndReturnElement(LocatorOf.statusIcon);
        return statusIcon.getCssValue("background-color").equals("rgba(92, 184, 92, 1)"); // green
    }
}
