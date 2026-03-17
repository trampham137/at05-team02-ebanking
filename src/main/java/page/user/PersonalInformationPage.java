package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class PersonalInformationPage {
    private final By emailLocator = By.id("j_idt24:j_idt38");

    public String getEmailAddress() {
        return DriverUtils.DRIVER.findElement(emailLocator).getText();
    }
}
