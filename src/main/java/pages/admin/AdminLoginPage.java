package pages.admin;

import base.BasePage;
import io.qameta.allure.Step;
import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

public class AdminLoginPage extends BasePage {
    private final By usernameTextboxLocator = By.xpath("//td[label[text()='ID']]//following-sibling::td//input");
    private final By passwordTextboxLocator = By.xpath("//input[@type='password']");
    private final By loginButtonLocator = By.xpath("//button[@type='submit']");

    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login admin with username: {username}")
    public void login(String username, String password) {
        type(usernameTextboxLocator, username);
        type(passwordTextboxLocator, password);
        click(loginButtonLocator);
    }
}
