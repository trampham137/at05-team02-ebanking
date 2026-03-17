package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;
import pages.auth.LoginPage;
import pages.transfer.interbank.InterbankTransferPage;
import pages.transfer.internal.InternalTransferPage;

public class SidebarComponent extends BasePage {
    public By getMenuItem(String menuName) {
        return By.xpath("//a[.//span[text()='" + menuName + "']]");
    }

    private final By accountMenuLocator = getMenuItem("Tài khoản");
    private final By openAccountMenuLocator = getMenuItem("Mở tài khoản");
    private final By transactionLogMenuLocator = getMenuItem("Nhật kí giao dịch");
    private final By internalTransferMenuLocator = getMenuItem("Chuyển khoản");
    private final By interbankTransferMenuLocator = getMenuItem("Liên ngân hàng");
    private final By logoutMenuLocator = getMenuItem("Đăng xuất");

    public SidebarComponent(WebDriver driver) {
        super(driver);
    }

    public DashboardPage goToAccounts() {
        click(accountMenuLocator);
        return new DashboardPage(driver);
    }

    public OpenAccountPage goToOpenAccount() {
        click(openAccountMenuLocator);
        return new OpenAccountPage(driver);
    }

    // public TransactionLog goToTransactionLogMenu() {
    //     click(transactionLogMenuLocator);
    //     return new TransactionLog(driver);
    // }

    public InternalTransferPage goToInternalTransfer() {
        click(internalTransferMenuLocator);
        return new InternalTransferPage(driver);
    }

    public InterbankTransferPage goToInterbankTransfer() {
        click(interbankTransferMenuLocator);
        return new InterbankTransferPage(driver);
    }

    public LoginPage logout() {
        click(logoutMenuLocator);
        return new LoginPage(driver);
    }
}
