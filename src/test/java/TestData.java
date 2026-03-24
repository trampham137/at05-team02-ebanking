import models.*;
import models.enums.City;
import models.enums.Gender;

import java.time.LocalDate;

public class TestData {
    public static final User STANDARD_USER = new User("lyy_test", "123456789");
    public static final User ADMIN_USER = new User("1", "admin");

    private TestData() {
    }

    public static RegisterData validRegister(String prefix) {
        long timestamp = System.currentTimeMillis() % 100000;

        String username = prefix + "_" + timestamp;
        String email = username + "@mailinator.com";
        String fullName = "testing_note_" + username;

        return new RegisterData(
                username,
                "1234567889",
                "1234567889",
                fullName,
                "01234567899",
                LocalDate.of(2000, 1, 1),
                Gender.FEMALE,
                City.DA_NANG,
                "01234567899",
                email
        );
    }

    // TODO: amount should be number >> DONE
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
