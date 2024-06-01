import pages.*;
import exceptions.*;

import org.junit.*;
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

public class ArtMoziTest {
    public WebDriver driver;

    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=hu");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("intl.accept_languages", "hu,hu_HU");
        options.setExperimentalOption("prefs", chromePrefs);
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginSuccess() throws InvalidCredentialsException {
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.validateTitle());

        LoginPage loginPage = mainPage.goToLogin();
        Assert.assertTrue(loginPage.validateTitle());

        ProfilePage profilePage = loginPage.login("pt46p3", "seleniumTest2024");
        Assert.assertTrue(profilePage.validateTitle());
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testLoginFailure() throws InvalidCredentialsException {
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.validateTitle());

        LoginPage loginPage = mainPage.goToLogin();
        Assert.assertTrue(loginPage.validateTitle());

        loginPage.login("invalidUser", "invalidPassword");
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
