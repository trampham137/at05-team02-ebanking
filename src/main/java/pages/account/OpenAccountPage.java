package pages.account;

import components.SidebarComponent;
import models.OpenAccountData;
import org.openqa.selenium.By;
import base.BasePage;
import org.openqa.selenium.WebDriver;

public class OpenAccountPage extends BasePage {
    private final SidebarComponent sidebar;

    private final By accountTypeDropDownLocator = By.xpath("//div[label[contains(text(), 'Chọn tài khoản')]]");
    private final By createAccountButtonLocator = By.cssSelector("input[type=submit]");
    private final By successPopupMessageLocator = By.xpath("//div[contains(text(),'Mở tài khoản thành công')]");
    private final By closePopupButtonLocator = By.cssSelector("#primefacesmessagedlg a.ui-dialog-titlebar-close");

    public OpenAccountPage(WebDriver driver) {
        super(driver);
        this.sidebar = new SidebarComponent(driver);
    }

    public SidebarComponent sidebar() {
        return sidebar;
    }

    private By accountTypeOptionLocator(String accountType) {
        return By.xpath(String.format("//ul[contains(@class, 'ui-selectonemenu-list')]//li[text()='%s']", accountType));
    }

    public void createAccount(OpenAccountData data) {
        click(accountTypeDropDownLocator);
        click(accountTypeOptionLocator(data.getAccountType()));
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
