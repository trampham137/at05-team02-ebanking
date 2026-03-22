package pages.account;

import base.UserBasePage;
import models.OpenAccountData;
import models.enums.AccountType;
import org.openqa.selenium.By;
import base.BasePage;
import org.openqa.selenium.WebDriver;

public class OpenAccountPage extends UserBasePage {
    private final By accountTypeDropDownLocator = By.xpath("//div[label[contains(text(), 'Chọn tài khoản')]]");
    private final By createAccountButtonLocator = By.cssSelector("input[type=submit]");
    private final By successPopupMessageLocator = By.xpath("//div[contains(text(),'Mở tài khoản thành công')]");
    private final By closePopupButtonLocator = By.cssSelector("#primefacesmessagedlg a.ui-dialog-titlebar-close");

    public OpenAccountPage(WebDriver driver) {
        super(driver);
    }

    private By accountTypeOptionLocator(String accountType) {
        return By.xpath(String.format("//ul[contains(@class, 'ui-selectonemenu-list')]//li[text()='%s']", accountType));
    }

    public void createAccount(AccountType accountType) {
        click(accountTypeDropDownLocator);
        click(accountTypeOptionLocator(accountType.getDisplayName()));
        click(createAccountButtonLocator);
    }

    public boolean isOpenAccountSuccessPopupDisplayed() {
        return isDisplayed(successPopupMessageLocator);
    }

    public String getOpenAccountSuccessPopupMessage() {
        return getText(successPopupMessageLocator).trim();
    }

    public void closeSuccessPopup() {
        click(closePopupButtonLocator);
    }
}
