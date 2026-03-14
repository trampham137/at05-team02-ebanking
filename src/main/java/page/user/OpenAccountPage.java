package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class OpenAccountPage {
    // TODO: fix duplicate
    private final By accountTypeDropDownLocator = By.xpath("//div//label[text()='Chọn tài khoản']");
    private final By nonTermDepositAccountTypeLocator = By.xpath("//div//li[text()='Tài Khoản kỳ gửi không kỳ hạn']");
    private final By savingAccountTypeLocator = By.xpath("//div//li[text()='Tài Khoản tiết kiệm']");

    private final By createAccountButton = By.xpath("//td//input[@value='Tạo tài khoản']");

    public void clickAccountTypeDropDown() {
        DriverUtils.DRIVER.findElement(accountTypeDropDownLocator).click();
    }

    public void clickNonTermDepositAccountType() {
        DriverUtils.DRIVER.findElement(nonTermDepositAccountTypeLocator).click();
    }

    public void clickSavingAccountType() {
        DriverUtils.DRIVER.findElement(savingAccountTypeLocator).click();
    }

    public void clickCreateAccountButton() {
        DriverUtils.DRIVER.findElement(createAccountButton).click();
    }

    public void openAccountNonTermDepositAccountType() {
        clickAccountTypeDropDown();
        clickNonTermDepositAccountType();
        clickCreateAccountButton();
    }

    public void openAccountSavingAccountType() {
        clickAccountTypeDropDown();
        clickSavingAccountType();
        clickCreateAccountButton();
    }

    //TODO: add function - dynamic select account type
}
