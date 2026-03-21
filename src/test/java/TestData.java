import models.DepositData;
import models.InternalTransferData;
import models.OpenAccountData;
import models.User;

public class TestData {
    public static final User STANDARD_USER = new User("lyy_test", "123456789");
    public static final User ADMIN_USER = new User("1", "admin");

    private TestData() {
    }

    // TODO: 2 account type, should be static variable
    public static OpenAccountData validOpenAccount() {
        return new OpenAccountData("Tài Khoản kỳ gửi không kỳ hạn");
    }

    // TODO: amount > number
    public static DepositData validDeposit() {
        return new DepositData("100002274", "100000", "Testing");
    }

    public static DepositData deposit(String account, String amount, String description) {
        return new DepositData(account, amount, description);
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
