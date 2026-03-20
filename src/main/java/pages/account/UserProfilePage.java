package pages.account;

import base.UserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

public class UserProfilePage extends UserBasePage {
    private final By emailLocator = By.id("j_idt24:j_idt38");

    public UserProfilePage(WebDriver driver) {
        super(driver);
    }


    public String getEmailAddress() {

        return DriverUtils.getDriver().findElement(emailLocator).getText();
    }
}
