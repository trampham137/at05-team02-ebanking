package base;

import org.openqa.selenium.By;
import utils.DriverUtils;
import utils.WaitUtils;

public class BaseAdminPage {
    // TODO: Bat theo link text
    private final By customerListMenuLocator = By.linkText("Danh sách khách hàng");
    private final By depositMenuLocator = By.linkText("Nộp Tiền");
    private final By withdrawMenuLocator = By.linkText("Rút tiền");
    private final By byCustomerMenuLocator = By.linkText("Theo Khách Hàng");
    private final By byDateMenuLocator = By.linkText("Theo Ngày");
    private final By logoutButtonLocator = By.xpath("//input[@value='Thoát']");

    public void goToCustomerListPage() {
        WaitUtils.waitForElementVisible(customerListMenuLocator);
        DriverUtils.DRIVER.findElement(customerListMenuLocator).click();
    }

    public void goToDepositPage() {
        WaitUtils.waitForElementVisible(depositMenuLocator);
        DriverUtils.DRIVER.findElement(depositMenuLocator).click();
    }

    public void goToWithdrawPage() {
        WaitUtils.waitForElementVisible(withdrawMenuLocator);
        DriverUtils.DRIVER.findElement(withdrawMenuLocator).click();
    }

    public void goToByCustomerPage() {
        WaitUtils.waitForElementVisible(byCustomerMenuLocator);
        DriverUtils.DRIVER.findElement(byCustomerMenuLocator).click();
    }

    public void goToByDatePage() {
        WaitUtils.waitForElementVisible(byDateMenuLocator);
        DriverUtils.DRIVER.findElement(byDateMenuLocator).click();
    }

    public void Logout() {
        WaitUtils.waitForElementVisible(logoutButtonLocator);
        DriverUtils.DRIVER.findElement(logoutButtonLocator).click();
    }
}
