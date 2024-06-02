package pages;

import data.RegistrationData;

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
        static By firstNameInput        = By.xpath("//form[@action='/user/register']//input[@id='edit-art-forename-0-value']");
        static By lastNameInput         = By.xpath("//form[@action='/user/register']//input[@id='edit-art-surname-0-value']");
        static By emailInput            = By.xpath("//form[@action='/user/register']//input[@id='edit-mail']");
        static By usernameInput         = By.xpath("//form[@action='/user/register']//input[@id='edit-name']");
        static By newsletterCheckbox    = By.xpath("//form[@action='/user/register']//label[@for='edit-art-mailchimp-0-value-subscribe']");
        static By privacyPolicyCheckbox = By.xpath("//form[@action='/user/register']//label[@for='edit-art-privacy-policy-value']");
        static By registerButton        = By.xpath("//form[@action='/user/register']//button[@id='edit-submit']");
    };

    public RegisterPage(WebDriver driver) {
        super(driver, "https://artmozi.hu/user/register");
    }

    @Override
    protected String expectedTitle() {
        return "Új fiók létrehozása";
    }

    public void register(RegistrationData data) {
        WebElement firstNameInput        = this.waitAndReturnElement(LocatorOf.firstNameInput);
        WebElement lastNameInput         = this.waitAndReturnElement(LocatorOf.lastNameInput);
        WebElement emailInput            = this.waitAndReturnElement(LocatorOf.emailInput);
        WebElement usernameInput         = this.waitAndReturnElement(LocatorOf.usernameInput);
        WebElement newsletterCheckbox    = this.waitAndReturnElement(LocatorOf.newsletterCheckbox);
        WebElement privacyPolicyCheckbox = this.waitAndReturnElement(LocatorOf.privacyPolicyCheckbox);
        WebElement registerButton        = this.waitAndReturnElement(LocatorOf.registerButton);

        firstNameInput.sendKeys(data.getFirstName());
        lastNameInput.sendKeys(data.getLastName());
        emailInput.sendKeys(data.getEmail());
        usernameInput.sendKeys(data.getUsername());
        this.clickOn(privacyPolicyCheckbox);
        if (data.isSubscribedToNewsletter()) {
            this.clickOn(newsletterCheckbox);
        }
        this.clickOn(registerButton);
    }
}
