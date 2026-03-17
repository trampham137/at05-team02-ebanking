package pages.account;

import base.BasePage;
import base.UserBasePage;
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

    public String getAccountName() {
        return getText(accountNameValueLocator);
    }

    public String getAccountType() {
        return getText(accountTypeValueLocator);
    }

    public long getBalance() {
        return parseCurrencyToLong(getText(balanceValueLocator));
    }

}
