package testdata;

import models.DepositData;
import models.OpenAccountData;
import pages.admin.DepositMoneyPage;

public class TestDataFactory {
    private TestDataFactory() {
    }

    public static OpenAccountData defaultOpenAccountData() {
        return new OpenAccountData("Tài Khoản kỳ gửi không kỳ hạn");
    }

    public static DepositData defaultDepositMoneyData() {
        return new DepositData("100002274", "100000", "Testing");
    }
}
