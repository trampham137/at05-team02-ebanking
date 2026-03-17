package page.admin;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class AdminLoginPage {
    private final By idTextBoxLocator = By.xpath("//td[label[text()='ID']]//following-sibling::td//input");
    private final By passwordTextBoxLocator = By.xpath("//input[@type='password']");
    private final By loginButtonLocator = By.xpath("//button[@type='submit']");

    public void enterID(String id) {
        DriverUtils.DRIVER.findElement(idTextBoxLocator).sendKeys(id);
    }

    public void enterPassword(String password) {
        DriverUtils.DRIVER.findElement(passwordTextBoxLocator).sendKeys(password);
    }

    public void clickButtonLogin() {
        DriverUtils.DRIVER.findElement(loginButtonLocator).click();
    }

    public void Login(String id, String password) {
        enterID(id);
        enterPassword(password);
        clickButtonLogin();
    }
}
