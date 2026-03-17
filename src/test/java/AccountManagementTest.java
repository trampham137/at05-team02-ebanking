import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;

public class AccountManagementTest extends BaseTest {

    @Test(description = "EB-01 Verify user can create a new bank account and the initial balance is displayed correctly.")
    public void EB01_user_can_open_new_bank_account() {
        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);

        int beforeRowCount = dashboardPage.accountTable().getRowCount();

        OpenAccountPage openAccountPage = dashboardPage.sidebar().goToOpenAccount();
        openAccountPage.createAccount(TestData.validOpenAccount());

        Assert.assertTrue(openAccountPage.isOpenAccountSuccessPopupDisplayed());
        Assert.assertEquals(openAccountPage.getOpenAccountSuccessPopupMessage(), "Mở tài khoản thành công");
        openAccountPage.closeSuccessPopup();

        DashboardPage refreshedDashboardPage = openAccountPage.sidebar().goToAccounts();

        int afterRowCount = refreshedDashboardPage.accountTable().getRowCount();
        Assert.assertEquals(beforeRowCount + 1, afterRowCount);

        // TODO: check position
        String newAccountNumber = refreshedDashboardPage.getLastAccountNumber();
        Assert.assertFalse(newAccountNumber.isBlank());

        AccountDetailPage detailPage = refreshedDashboardPage.openAccountDetail(newAccountNumber);
        Assert.assertEquals(detailPage.getBalance(), 0L);
        IO.println(newAccountNumber);
    }

}
