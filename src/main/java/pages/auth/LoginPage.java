package pages.auth;

import base.BasePage;
import base.UserBasePage;
import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.account.DashboardPage;

public class LoginPage extends UserBasePage {
    private final By usernameTextboxLocator = By.cssSelector("form input[type=text]");
    private final By passwordTextboxLocator = By.cssSelector("form input[type=password]");
    private final By loginButtonLocator = By.cssSelector("form input[type=submit]");
    private final By registerLink = By.linkText("Tạo tài khoản");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameTextboxLocator, username);
        type(passwordTextboxLocator, password);
        click(loginButtonLocator);
    }

    public void login(User user) {
        login(user.getUsername(), user.getPassword());
    }

    public void clickRegisterLink() {
        click(registerLink);
    }
}
