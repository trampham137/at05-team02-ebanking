package example;

import org.testng.annotations.Test;
import page.admin.AdminLoginPage;
import base.BaseAdminPage;
import page.admin.DepositPage;
import page.user.WithdrawPage;

public class TestAdmin extends TestBase {
    // Set up the test environment
    AdminLoginPage loginPage = new AdminLoginPage();
    BaseAdminPage baseAdminPage = new BaseAdminPage();
    DepositPage depositPage = new DepositPage();
    WithdrawPage withdrawPage = new WithdrawPage();

    @Test
    public void verifyLogin() throws InterruptedException {
        loginPage.Login("1", "admin");
        Thread.sleep(3000);// khong dung thread ow day sua sau
        //    adminSidebarPage.goToDepositPage();
        //    depositPage.enterDepositForm(100002267,10000, "ck");
        //   depositPage.clickConfirmButton();
        baseAdminPage.goToWithdrawPage();
        withdrawPage.enterWithdrawForm(100002267, 5000, "ck");
        withdrawPage.clickTransactionButton();
    }
}
