package pages.auth;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountActivatedPage extends BasePage {
    private final By successMessage = By.xpath("//*[contains(text(),'KÍCH HOẠT TÀI KHOẢN THÀNH CÔNG')]");

    public AccountActivatedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isActivationSuccessDisplayed() {
        waitVisible(successMessage);
        return isDisplayed(successMessage);
    }
}
