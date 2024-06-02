package pages;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public abstract class PageBase {

    protected WebDriver driver;
    protected JavascriptExecutor js;
    protected WebDriverWait wait;

    public PageBase(WebDriver driver, String URL) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, 20);
        this.driver.get(URL);
    }

    public Boolean validateTitle() {
        return this.driver.getTitle().contains(this.expectedTitle());
    }

    abstract protected String expectedTitle();

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

    public void clickOn(WebElement target) {
        js.executeScript("arguments[0].click();", target);
    }

    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    public void screenshot(String outputPath) {
        try {
            File scrFile = ((TakesScreenshot)(this.driver)).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(outputPath));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
