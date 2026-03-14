package base;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class BasePage {

    private final String dynamicMenuLocator = "//a[span[text()='%s']]";
    private final By personalInformationLocator = getMenuItemLocator("Thông tin cá nhân");
    private final By accountLocator = getMenuItemLocator("Tài khoản");
    private final By openAccountLocator = getMenuItemLocator("Mở tài khoản");
    private final By changePassWordLocator = getMenuItemLocator("Đổi mật khẩu");
    private final By transactionHistoryLocator = getMenuItemLocator("Nhật kí giao dịch");
    private final By internalTransferLocator = getMenuItemLocator("Chuyển  khoản");
    private final By externalTransferLocator = getMenuItemLocator("Liên Ngân Hàng");
    private final By logoutLocator = getMenuItemLocator("Đăng xuất");
    private final String dynamicDropdownLabelLocator = "//label[text()='%s']";
    private final String dynamicDropdownItemLocator = "//li[@data-label='%s']";
    private final String dynamicTextBoxLocator = "//td[label[text()='%s']]//following-sibling::td//input";

    public By getDropdownLabelLocator(String label) {
        return By.xpath(String.format(dynamicDropdownLabelLocator, label));
    }

    public By getDropdownItemLocator(String item) {
        return By.xpath(String.format(dynamicDropdownItemLocator, item));
    }

    public By getItemTextBoxLocator(String label) {
        return By.xpath(String.format(dynamicTextBoxLocator, label));
    }

    private By getMenuItemLocator(String item) {
        return By.xpath(String.format(dynamicMenuLocator, item));
    }
    // a[span[text() >> duplicate

    public void goToPersonalInformationPage() {
        DriverUtils.DRIVER.findElement(personalInformationLocator).click();
    }

    public void goToAccountPage() {
        DriverUtils.DRIVER.findElement(accountLocator).click();
    }

    public void goToOpenAccountPage() {
        DriverUtils.DRIVER.findElement(openAccountLocator).click();
    }

    public void goToChangePasswordPage() {
        DriverUtils.DRIVER.findElement(changePassWordLocator).click();
    }

    public void goToTransactionHistoryPage() {
        DriverUtils.DRIVER.findElement(transactionHistoryLocator).click();
    }

    public void goToInternalTransferPage() {
        DriverUtils.DRIVER.findElement(internalTransferLocator).click();
    }

    public void goToExternalTransfer() {
        DriverUtils.DRIVER.findElement(externalTransferLocator).click();
    }

    public void goToLogoutPage() {
        DriverUtils.DRIVER.findElement(logoutLocator).click();
    }


}
