import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.DashboardPage;
import pages.admin.AdminDashboardPage;
import pages.admin.DepositMoneyPage;
import utils.TextUtils;

import java.text.Normalizer;

public class AdminManagementTest extends BaseTest {
    @Test(description = "EB-02 Verify admin can deposit money to a user account and user balance is increased correctly.")
    public void EB02_admin_can_deposit_money_to_user_account() {
        AdminDashboardPage adminDashboardPage = loginAsAdmin();

        DepositMoneyPage depositMoneyPage = adminDashboardPage.goToDepositMoney();
        depositMoneyPage.depositToAccount(TestData.validDeposit());

        Assert.assertTrue(depositMoneyPage.isDepositSuccessful());
        Assert.assertEquals(depositMoneyPage.getSuccessMessage(), "nộp tiền thành công");
    }
}
