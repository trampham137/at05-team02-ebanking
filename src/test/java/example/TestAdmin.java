package example;

import org.testng.annotations.Test;
import page.admin.AdminLoginPage;
import base.BaseAdminPage;
import page.admin.DepositPage;

public class TestAdmin extends TestBase {
    // Set up the test environment
    AdminLoginPage adminLoginPage = new AdminLoginPage();
    BaseAdminPage baseAdminPage = new BaseAdminPage();
    DepositPage depositPage = new DepositPage();

    @Test
    public void verifyAdminLogin() {
        adminLoginPage.Login("1", "admin");

        baseAdminPage.goToCustomerListPage();
        // adminSidebarPage.Logout();
        // adminSidebarPage.goToByDatePage();
        // adminSidebarPage.goToWithdrawPage();
        // adminSidebarPage.goToByCustomerPage();
        // adminSidebarPage.goToDepositPage();

    }

    @Test
    public void verifyDepositPage() {
        adminLoginPage.Login("1", "admin");
        baseAdminPage.goToDepositPage();
        depositPage.enterDepositForm(100002267, 50000, "ck");
        depositPage.clickConfirmButton();
    }

}
