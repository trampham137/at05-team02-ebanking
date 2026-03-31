import base.BaseTest;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.transfer.InternalTransferPage;

public class EB_06 extends BaseTest {
    DashboardPage dashboardPage;

    @Test(description = "Verify system displays required validation messages when required fields are left empty on the internal transfer form.")

    public void EB_06_verify_required_fields_validation() {
        // Prepare test data
        RegisterData registerData = TestData.validRegister("lyy_test");
        User user = new User(registerData.getUsername(), registerData.getPassword());

        // PHASE 1: Register + login
        registerAndActivateUser(registerData);
        dashboardPage = loginAsUser(user);

        // PHASE 2: Open account
        String accountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);

        // PHASE 3: Go to Internal Transfer
        AccountDetailPage accountDetailPage = dashboardPage.openAccountDetail(accountNumber);
        InternalTransferPage transferPage = accountDetailPage.goToInternalTransfer();

        transferPage.clearReceiverAccount();

        // PHASE 4: Leave all fields empty and click Confirm
        transferPage.clickConfirm();

        Assert.assertTrue(transferPage.isSourceAccountRequiredMessageDisplayed(), "Không hiển thị message: Mời chọn tài khoản");
        Assert.assertTrue(transferPage.isAmountRequiredMessageDisplayed(), "Không hiển thị message: Nhập số tiền");
        Assert.assertTrue(transferPage.isContentRequiredMessageDisplayed(), "Không hiển thị message: Nhập nội dung");
    }
}
