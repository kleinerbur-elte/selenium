package pages;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;

public class RegisterPage extends PageBase {

    private static class LocatorOf {
        static final By firstnameInput  = By.xpath("//form[@action='/user/register']//input[@id='edit-art-forename-0-value']");
        static final By surnameInput    = By.xpath("//form[@action='/user/register']//input[@id='edit-art-surname-0-value']");
        static final By emailInput      = By.xpath("//form[@action='/user/register']//input[@id='edit-mail']");
        static final By usernameInput   = By.xpath("//form[@action='/user/register']//input[@id='edit-name']");
        static final By newsletterInput = By.xpath("//form[@action='/user/register']//label[@for='edit-art-mailchimp-0-value-subscribe']");
        static final By privPolicyInput = By.xpath("//form[@action='/user/register']//label[@for='edit-art-privacy-policy-value']");
        static final By registerButton  = By.xpath("//form[@action='/user/register']//button[@id='edit-submit']");
    };

    public RegisterPage(WebDriver driver) {
        super(driver, "https://artmozi.hu/user/register");
    }

    @Override
    protected String expectedTitle() {
        return "Új fiók létrehozása";
    }

    public void register(String firstname, String surname, String email, String username, Boolean newsletter) {
        this.waitAndReturnElement(LocatorOf.firstnameInput).sendKeys(firstname);
        this.waitAndReturnElement(LocatorOf.emailInput).sendKeys(email);
        this.waitAndReturnElement(LocatorOf.usernameInput).sendKeys(username);
        this.waitAndReturnElement(LocatorOf.surnameInput).sendKeys(surname);
        if (newsletter) {
            this.clickOn(this.waitAndReturnElement(LocatorOf.newsletterInput));
        }
        this.clickOn(this.waitAndReturnElement(LocatorOf.privPolicyInput));
        this.clickOn(this.waitAndReturnElement(LocatorOf.registerButton));
    }
}
