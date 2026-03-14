package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;
import models.User;

public class LoginPage {
    private final By AccountTextBoxLocator = By.xpath("//input[@type=\"text\"]");
    private final By PassWordTextBoxLocator = By.xpath("//input[@type=\"password\"]");
    private final By LoginButtonLocator = By.xpath("//p//input[@value=\"Đặng nhập\"]");

    public void enterAccount(String account) {
        DriverUtils.DRIVER.findElement(AccountTextBoxLocator).sendKeys(account);
    }

    public void enterPassWord(String password) {
        DriverUtils.DRIVER.findElement(PassWordTextBoxLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        DriverUtils.DRIVER.findElement(LoginButtonLocator).click();
    }

    public void Login(User user) {
        enterAccount(user.getAccount());
        enterPassWord(user.getPassword());
        clickLoginButton();
    }
}
