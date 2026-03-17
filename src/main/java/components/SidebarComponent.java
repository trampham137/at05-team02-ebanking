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
    private final By accountMenuLocator = By.xpath("//a[.//span[text()='Tài khoản']]");
    private final By openAccountMenuLocator = By.xpath("//a[.//span[text()='Mở tài khoản']]");
    private final By transactionLogMenuLocator = By.xpath("//a[.//span[text()='Nhật kí giao dịch']]");
    private final By internalTransferMenuLocator = By.xpath("//a[.//span[text()='Chuyển khoản']]");
    private final By interbankTransferMenuLocator = By.xpath("//a[.//span[text()='Liên ngân hàng']]");
    private final By logoutMenuLocator = By.xpath("//a[.//span[text()='Đăng xuất']]");

    public SidebarComponent(WebDriver driver) {
        super(driver);
    }

    public DashboardPage goToAccounts() {
        System.out.println(find(accountMenuLocator).getText());
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
