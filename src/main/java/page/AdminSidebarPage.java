package page;

import org.openqa.selenium.By;
import untils.DriverUtils;
import untils.WaitUtils;

public class AdminSidebarPage {
    private final String sidebarMenuXpath = "//a[span[text()='%s']]";

    private By getSidebarMenuLocator(String item) {
        return By.xpath(String.format(sidebarMenuXpath, item));
    }

    private final By customerListMenuLocator = getSidebarMenuLocator("Danh sách khách hàng");
    private final By depositMenuLocator = getSidebarMenuLocator("Nộp Tiền");
    private final By withdrawMenuLocator = getSidebarMenuLocator("Rút tiền");
    private final By byCustomerMenuLocator = getSidebarMenuLocator("Theo Khách Hàng");
    private final By byDateMenuLocator = getSidebarMenuLocator("Theo Ngày");


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
