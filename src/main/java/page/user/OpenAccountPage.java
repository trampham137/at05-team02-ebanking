package page.user;

import base.BaseUserPage;
import enums.AccountType;
import org.openqa.selenium.By;
import utils.DriverUtils;

public class OpenAccountPage extends BaseUserPage {
    private final By accountTypeDropDownLocator = getDropdownLabelLocator("Chọn tài khoản");
    private final By nonTermDepositAccountTypeLocator =getDropdownItemLocator("Tài Khoản kỳ gửi không kỳ hạn");
    private final By savingAccountTypeLocator = getDropdownItemLocator("Tài Khoản tiết kiệm");
    private final By createAccountButton = By.xpath("//td//input[@value='Tạo tài khoản']");
    private final By closeNotificationPopupButtonLocator = By.xpath("//a[@role='button']");
    public void clickAccountTypeDropDown() {
        DriverUtils.DRIVER.findElement(accountTypeDropDownLocator).click();
    }
    public void clickNonTermDepositAccountType(){
        DriverUtils.DRIVER.findElement(nonTermDepositAccountTypeLocator).click();
    }
    public void clickSavingAccountType(){
        DriverUtils.DRIVER.findElement(savingAccountTypeLocator).click();
    }
    public void clickCreateAccountButton(){
        DriverUtils.DRIVER.findElement(createAccountButton).click();
    }
    public void openAccountNonTermDepositAccountType(){
        clickAccountTypeDropDown();
        clickNonTermDepositAccountType();
        clickCreateAccountButton();
        closeNotificationPopup();
    }
    public void openAccountSavingAccountType(){
        clickAccountTypeDropDown();
        clickSavingAccountType();
        clickCreateAccountButton();
        closeNotificationPopup();
    }
    public void selectAccountType(AccountType type) {
        if (type == AccountType.NON_TERM) {
            openAccountNonTermDepositAccountType();
        } else if (type == AccountType.SAVING) {
            openAccountSavingAccountType();
        }
    }

    public void closeNotificationPopup() {
        DriverUtils.DRIVER.findElement(closeNotificationPopupButtonLocator).click();
    }
}
