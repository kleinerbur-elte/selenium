package pages;

import org.junit.*;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends PageBase {
    public ConfirmationPage(WebDriver driver, String URL) {
        super(driver, URL);
    }

    @Override
    protected String expectedTitle() {
        return "Jelszó visszaállítása";
    }
}
