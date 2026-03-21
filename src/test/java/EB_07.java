import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.transfer.internal.InternalTransferPage;

public class EB_07 extends BaseTest {

    @Test(description = "Verify system prevents internal transfer when transfer amount is greater than available balance.")
    public void verifyInsufficientBalanceOnTransfer() {
        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);
        int index = 1;
        String accountSource = dashboardPage.getAccountByIndex(index);
        AccountDetailPage accountDetailPage = dashboardPage.openAccountDetail(accountSource);
        long balance = accountDetailPage.getBalance();

        DashboardPage dashboardPage1 = accountDetailPage.goToAccounts();
        int index1 = 2;
        String accountReceiver = dashboardPage1.getAccountByIndex(index1);

        InternalTransferPage internalTransferPage = accountDetailPage.goToInternalTransfer();
        internalTransferPage.selectSourceAccount(accountSource);
        internalTransferPage.enterReceiverAccount(accountReceiver);
        internalTransferPage.enterAmount(balance + 10000);
        internalTransferPage.enterDescription("ck");
        internalTransferPage.clickConfirmButton();
        Assert.assertEquals(internalTransferPage.getAmountGreaterThanBalanceMessage(), "Số tiền vượt mức", "Validation messages are incorrect");
    }
}
