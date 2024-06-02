package pages;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;
import java.util.List;

public class MinuteInbox extends PageBase {
    private String email;

    private static class LocatorOf {
        static By mailAddress  = By.xpath("//div[@class='emailBlock']//span[@id='email']");
        static By inbox        = By.xpath("//tbody[@id='schranka']");
        static By incomingMail = By.xpath("//tbody[@id='schranka']//tr//td[@class='from']");
    };

    public MinuteInbox(WebDriver driver) {
        super(driver, "https://minuteinbox.com");
    }

    @Override
    protected String expectedTitle() {
        return "MinuteInbox";
    }

    public String getEmailAddress() {
        if (this.email == null) {
            this.email = this.waitAndReturnElement(LocatorOf.mailAddress).getText();
        }
        return this.email;
    }

    public String getUsername() {
        if (this.email == null) {
            getEmailAddress();
        }
        String username = this.email.split("@")[0];
        return username;
    }

    public void waitForNewIncomingMail(int timeout) {
        this.driver.navigate().refresh();
        int currentInboxSize = this.driver.findElements(LocatorOf.incomingMail).size();
        WebDriverWait keepCheckingInbox = new WebDriverWait(this.driver, timeout);
        ExpectedCondition<List<WebElement>> signOfNewMail =
            ExpectedConditions.numberOfElementsToBeMoreThan(LocatorOf.incomingMail, currentInboxSize);
        keepCheckingInbox.until(signOfNewMail);
    }

    public String getLatestMail() {
        WebElement inbox      = this.waitAndReturnElement(LocatorOf.inbox);
        List<WebElement> mail = inbox.findElements(By.tagName("tr"));
        WebElement latest = mail.get(mail.size() - 1);
        this.clickOn(latest);
        return this.getBodyText();
    }
}
