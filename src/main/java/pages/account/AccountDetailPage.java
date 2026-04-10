package pages.account;

import base.BasePage;
import base.UserBasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDetailPage extends UserBasePage {
    private By valueByLabel(String label) {
        return By.xpath("//td[.//label[text()='" + label + "']]/following-sibling::td");
    }

    private final By accountNameValueLocator = valueByLabel("Tên tài khoản:");
    private final By accountTypeValueLocator = valueByLabel("Loại tài khoản:");
    private final By balanceValueLocator = valueByLabel("Số dư tài khoản");

    public AccountDetailPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get account name")
    public String getAccountName() {
        return getText(accountNameValueLocator);
    }

    @Step("Get account type")
    public String getAccountType() {
        return getText(accountTypeValueLocator);
    }

    @Step("Get account balance")
    public long getBalance() {
        return parseCurrencyToLong(getText(balanceValueLocator));
    }
}
