package pages.auth;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountActivatedPage extends BasePage {
    private final By successMessage = By.xpath("//*[contains(text(),'KÍCH HOẠT TÀI KHOẢN THÀNH CÔNG')]");

    public AccountActivatedPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check activation success message displayed")
    public boolean isActivationSuccessDisplayed() {
        return isDisplayed(successMessage);
    }
}
