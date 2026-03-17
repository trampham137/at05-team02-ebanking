package admin;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.admin.AdminDashboardPage;
import pages.admin.DepositMoneyPage;
import testdata.TestDataFactory;

public class AdminManagementTest extends BaseTest {
    @Test(description = "EB-02 Verify admin can deposit money to a user account and user balance is increased correctly.")
    public void EB02_admin_can_deposit_money_to_user_account() {
        AdminDashboardPage adminDashboardPage = loginAsAdmin();

        DepositMoneyPage depositMoneyPage = adminDashboardPage.sidebar().goToDepositMoney();
        depositMoneyPage.depositToAccount(TestDataFactory.defaultDepositMoneyData());

        Assert.assertTrue(depositMoneyPage.isDepositSuccessful());
        Assert.assertEquals(depositMoneyPage.getSuccessMessage(), "nộp tiền thành công");
    }
}
