package base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;
import pages.transfer.InterbankTransferPage;
import pages.transfer.InternalTransferPage;

public class UserBasePage extends BasePage {
    public By getMenuItem(String menuName) {
        return By.xpath("//a[.//span[text()='" + menuName + "']]");
    }

    private final By userProfileMenuLocator = getMenuItem("Thông tin cá nhân");
    private final By accountMenuLocator = getMenuItem("Tài khoản");
    private final By openAccountMenuLocator = getMenuItem("Mở tài khoản");
    private final By transactionLogMenuLocator = getMenuItem("Nhật kí giao dịch");
    private final By internalTransferMenuLocator = getMenuItem("Chuyển  khoản");
    private final By interbankTransferMenuLocator = getMenuItem("Liên Ngân Hàng");
    private final By logoutMenuLocator = getMenuItem("Đăng xuất");

    public UserBasePage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Accounts page")
    public DashboardPage goToAccounts() {
        click(accountMenuLocator);
        return new DashboardPage(driver);
    }

    @Step("Navigate to Open Account page")
    public OpenAccountPage goToOpenAccount() {
        click(openAccountMenuLocator);
        return new OpenAccountPage(driver);
    }

    @Step("Navigate to Internal Transfer page")
    public InternalTransferPage goToInternalTransfer() {
        click(internalTransferMenuLocator);
        return new InternalTransferPage(driver);
    }

    @Step("Navigate to Interbank Transfer page")
    public InterbankTransferPage goToInterbankTransfer() {
        click(interbankTransferMenuLocator);
        return new InterbankTransferPage(driver);
    }

    @Step("Logout user account")
    public void logout() {
        click(logoutMenuLocator);
    }
}
