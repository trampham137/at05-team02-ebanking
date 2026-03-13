package example;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import page.AdminLoginPage;
import page.AdminSidebarPage;
import page.DepositPage;
import untils.DriverUtils;

import java.util.HashMap;

public class TestAdmin extends TestBase {
    // Set up the test environment
    AdminLoginPage adminLoginPage = new AdminLoginPage();
    AdminSidebarPage adminSidebarPage = new AdminSidebarPage();
    DepositPage depositPage = new DepositPage();

    @Test
    public void verifyAdminLogin() {
        adminLoginPage.Login("1", "admin");
        // adminSidebarPage.goToCustomerListPage();
        // adminSidebarPage.Logout();
        // adminSidebarPage.goToByDatePage();
        // adminSidebarPage.goToWithdrawPage();
        // adminSidebarPage.goToByCustomerPage();
        // adminSidebarPage.goToDepositPage();

    }

    @Test
    public void verifyDepositPage() {
        adminLoginPage.Login("1", "admin");
        adminSidebarPage.goToDepositPage();
        depositPage.enterDepositForm(100002267, 50000, "ck");
        depositPage.clickConfirmButton();
    }

}
