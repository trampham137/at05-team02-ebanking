package pages.account;

import models.OpenAccountData;
import org.openqa.selenium.By;
import base.BasePage;
import org.openqa.selenium.WebDriver;

public class OpenAccountPage extends BasePage {
    private final By accountTypeDropDownLocator = By.xpath("//div//label[text()='Chọn tài khoản']");
    private final By createAccountButtonLocator = By.xpath("//td//input[@value='Tạo tài khoản']");
    private final By successPopupLocator = By.xpath("");

    public OpenAccountPage(WebDriver driver) {
        super(driver);
    }

    public void createAccount(OpenAccountData data) {
        selectByVisibleText(accountTypeDropDownLocator, data.getAccountType());
        click(createAccountButtonLocator);
    }

    public boolean isOpenAccountSuccessPopupDisplayed() {
        return isDisplayed(successPopupLocator);
    }
}
