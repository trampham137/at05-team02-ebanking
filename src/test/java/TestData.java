import models.*;
import models.enums.City;
import models.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestData {
    public static final User STANDARD_USER = new User("lyy_test", "123456789");
    public static final User ADMIN_USER = new User("1", "admin");

    private TestData() {
    }

    public static RegisterData validRegister(String prefix) {
        return validRegister(prefix, true);
    }

    public static RegisterData validRegister(String prefix, boolean useTimestamp) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHssSSS"));
        String suffix = useTimestamp ? "_" + timestamp : "";

        String username = prefix + suffix;
        String email = username + "@mailinator.com";
        String fullName = "fullName_" + username;

        return new RegisterData(
                username,
                "123456789",
                "123456789",
                fullName,
                "01234567899",
                LocalDate.of(2000, 1, 1),
                Gender.FEMALE,
                City.DA_NANG,
                "01234567899",
                email
        );
    }

    public static InterbankTransferData validInterbankTransferFrom(String sourceAccount, long amount) {
        return new InterbankTransferData(
                sourceAccount,
                "10001111",
                "Nguyen Van A",
                "Ngân hàng Đông Á",
                "Chi nhánh Đà Nẵng",
                "Interbank transfer test",
                amount
        );
    }
}
