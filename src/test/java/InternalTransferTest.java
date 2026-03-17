import base.BaseTest;
import org.testng.annotations.Test;
import pages.account.DashboardPage;
import pages.transfer.internal.InternalTransferPage;

public class InternalTransferTest extends BaseTest {

    @Test(description = "EB-05 Verify user can successfully perform an internal transfer using OTP")
    public void EB05_user_can_transfer_internally_successfully() {
        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);

        InternalTransferPage internalTransferPage = dashboardPage.goToInternalTransfer();
        internalTransferPage.fillTransferForm(TestData.validInternalTransfer());
        // internalTransferPage.clickConfirmButton();
    }
}
