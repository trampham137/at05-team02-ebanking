import models.DepositData;
import models.InternalTransferData;
import models.OpenAccountData;
import models.User;

public class TestData {
    public static final User STANDARD_USER = new User("lyy_test", "123456789");
    public static final User ADMIN_USER = new User("1", "admin");

    // TODO: 2 account type, should be static variable
    public static final String ACCOUNT_TYPE_CURRENT = "Tài Khoản kỳ gửi không kỳ hạn";
    public static final String ACCOUNT_TYPE_SAVINGS = "Tài Khoản tiết kiệm";

    private TestData() {
    }

    // TODO: amount > number
    public static DepositData validDeposit() {
        return new DepositData("100002274", 100_000, "Testing");
    }

    public static InternalTransferData validInternalTransfer() {
        return new InternalTransferData("100002315", "100002316", 10000, "Testing");
    }

    public static InternalTransferData validInternalTransferFrom(String sourceAccount) {
        return new InternalTransferData(sourceAccount, "100002270", 10000, "Testing");
    }

    // public static InternalTransferData internalTransfer(String sourceAccount, String targetAccount, String amount, String description) {
    //     return new InternalTransferData(sourceAccount, targetAccount, amount, description); >> use this
    // }
}
