import api.*;
import data.*;
import pages.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        RegistrationData data = new RegistrationData();

        TempMail tempMail = new TempMail(this.tempMailAccessKey);
        data.setEmail(tempMail.getEmailAddress());
        data.setUsername(tempMail.getUsername());

        RegisterPage registerPage = new RegisterPage(this.driver);
        registerPage.register(data);

        String link = tempMail.waitAndGetConfirmationLink(15);
        ConfirmationPage confirmationPage = new ConfirmationPage(this.driver, link);
        assertTrue(confirmationPage.validateTitle());
    }

    @Disabled("Page would crash upon switching tabs")
    @Test
    public void testRegisterThroughUI() {
        RegistrationData data = new RegistrationData();

        MinuteInbox minuteInbox = new MinuteInbox(this.driver);
        data.setEmail(minuteInbox.getEmailAddress());
        data.setUsername(minuteInbox.getUsername());

        ((JavascriptExecutor)(this.driver)).executeScript("window.open()");
        ArrayList<String> windows = new ArrayList<String>(this.driver.getWindowHandles());
        this.driver.switchTo().window(windows.get(1));

        RegisterPage registerPage = new RegisterPage(this.driver);
        registerPage.register(data);

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
        loginPage.login(new LoginData(username, password));
        if (shouldFail) {
            assertFalse(loginPage.isLoggedIn());
        } else {
            assertTrue(loginPage.isLoggedIn());
            loginPage.logout();
            assertFalse(loginPage.isLoggedIn());
        }
    }

    enum staticTestTarget {
        LoginPage,
        RegisterPage,
        MinuteInbox
    }
    @ParameterizedTest(name = "{0}")
    @EnumSource(staticTestTarget.class)
    public void staticPageTest(staticTestTarget target) {
        PageBase page = null;
        switch(target) {
            case LoginPage:
                page = new LoginPage(this.driver);
                break;
            case RegisterPage:
                page = new RegisterPage(this.driver);
                break;
            case MinuteInbox:
                page = new MinuteInbox(this.driver);
                break;
            default:
                throw new IllegalArgumentException();
        }
        assertTrue(page.validateTitle());
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
