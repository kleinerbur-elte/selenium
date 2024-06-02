import pages.*;
import api.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.*;

import java.net.URL;
import java.net.MalformedURLException;

import java.security.NoSuchAlgorithmException;

public class ArtMoziTest {

    public WebDriver driver;
    private String tempMailAccessKey = "";

    @BeforeEach
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=hu");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("intl.accept_languages", "hu,hu_HU");
        chromePrefs.put("profile.default_content_setting_values.cookies", 1);
        options.setExperimentalOption("prefs", chromePrefs);

        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Test
    public void testRegisterWithAPI() throws NoSuchAlgorithmException {
        TempMail mail = new TempMail(this.tempMailAccessKey);
        String emailAddress = mail.getEmailAddress();
        String username     = mail.getUsername();

        RegisterPage registerPage = new RegisterPage(this.driver);
        registerPage.register("John", "Doe", emailAddress, username, false);

        String link = mail.waitAndGetConfirmationLink(15);
        ConfirmationPage confirmationPage = new ConfirmationPage(this.driver, link);
        assertTrue(confirmationPage.validateTitle());
    }

    @Disabled("Page would crash upon switching tabs")
    @Test
    public void testRegisterThroughUI() {
        MinuteInbox minuteInbox = new MinuteInbox(this.driver);
        String emailAddress = minuteInbox.getEmailAddress();
        String username     = minuteInbox.getUsername();

        ((JavascriptExecutor)(this.driver)).executeScript("window.open()");
        ArrayList<String> windows = new ArrayList<String>(this.driver.getWindowHandles());
        this.driver.switchTo().window(windows.get(1));

        RegisterPage registerPage = new RegisterPage(this.driver);
        registerPage.register("John", "Doe", emailAddress, username, false);

        this.driver.switchTo().window(windows.get(0));

        minuteInbox.waitForNewIncomingMail(30);
        minuteInbox.screenshot("inbox.png");
    }

    @ParameterizedTest(name="user: {0}; password: {1}; shouldFail: {2}")
    @CsvFileSource(resources = "/login.csv")
    public void testLoginLogout(String username, String password, Boolean shouldFail) {
        LoginPage loginPage = new LoginPage(this.driver);
        assertTrue(loginPage.validateTitle());
        assertFalse(loginPage.isLoggedIn());
        loginPage.login(username, password);
        if (shouldFail) {
            assertFalse(loginPage.isLoggedIn());
        } else {
            assertTrue(loginPage.isLoggedIn());
            loginPage.logout();
            assertFalse(loginPage.isLoggedIn());
        }
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
