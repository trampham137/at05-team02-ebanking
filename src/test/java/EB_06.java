import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.DashboardPage;
import pages.transfer.internal.InternalTransferPage;

public class EB_06 extends BaseTest {
    @Test(description = "Verify system displays required validation messages when required fields are left empty on the internal transfer form.")
    public void verifyEmptyRequiredFieldsValidation() {
        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);
        int index = 1;
        String accountSource = dashboardPage.getAccountByIndex(index);
        System.out.println(accountSource);
        int index1 = 2;
        String accountReceiver = dashboardPage.getAccountByIndex(index1);
        System.out.println(accountReceiver);
        InternalTransferPage internalTransferPage = dashboardPage.goToInternalTransfer();
        internalTransferPage.clearReceiverAccount();
        internalTransferPage.clickConfirmButton();
        Assert.assertEquals(internalTransferPage.getSourceAccountRequiredMessage(), "Mời chọn tài khoản", "Validation messages are incorrect");
        //  Assert.assertEquals(internalTransferPage.getRequiredAmountMessage(), "Nhập số tiền", "Validation messages are incorrect");
        Assert.assertEquals(internalTransferPage.getRequiredContentMessage(), "Nhập nội dung", "Validation messages are incorrect");
    }
}
