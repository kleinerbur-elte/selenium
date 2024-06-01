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

import java.util.List;

import java.util.concurrent.TimeUnit;

class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected int defaultWaitDuration;
    protected TimeUnit defaultWaitUnits;
    protected String title;

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.defaultWaitDuration = 2;
        this.defaultWaitUnits = TimeUnit.SECONDS;
        this.wait = new WebDriverWait(driver, defaultWaitDuration);
    }

    public Boolean validateTitle() {
        return this.driver.getTitle().contains(this.title);
    }

    protected List<WebElement> returnElements(By locator) {
        return this.driver.findElements(locator);
    }

    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    protected WebElement returnElement(By locator) throws NoSuchElementException {
        return this.driver.findElement(locator);
    }

    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }

    public void implicitlyWait(int n, TimeUnit unit) {
        this.driver.manage().timeouts().implicitlyWait(n, unit);
    }

    public void implicitlyWait() {
        this.implicitlyWait(this.defaultWaitDuration, this.defaultWaitUnits);
    }
}
