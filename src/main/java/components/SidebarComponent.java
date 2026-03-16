package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SidebarComponent extends BasePage {
    private final By accountMenuLocator = By.linkText("Tài khoản");
    private final By openAccountMenuLocator = By.linkText("Mở tài khoản");
    private final By transactionLogMenuLocator = By.linkText("Nhật kí giao dịch");
    private final By internalTransferMenuLocator = By.linkText("Chuyển khoản");
    private final By interbankTransferMenuLocator = By.linkText("Liên ngân hàng");
    private final By logoutMenuLocator = By.linkText("Đăng xuất");

    public SidebarComponent(WebDriver driver) {
        super(driver);
    }

    public void goToAccount() {
        click(accountMenuLocator);
    }

    public void goToOpenAccount() {
        click(openAccountMenuLocator);
    }

    public void goToTransactionLogMenu() {
        click(transactionLogMenuLocator);
    }

    public void goToInternalTransferMenu() {
        click(internalTransferMenuLocator);
    }

    public void goToInterbankTransferMenu() {
        click(interbankTransferMenuLocator);
    }

    public void goToLogoutMenu() {
        click(logoutMenuLocator);
    }
}
