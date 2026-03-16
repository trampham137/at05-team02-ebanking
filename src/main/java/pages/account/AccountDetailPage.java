package pages.account;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDetailPage extends BasePage {
    private final By accountNameValueLocator = By.xpath("");
    private final By accountTypeValueLocator = By.xpath("");
    private final By balanceValueLocator = By.xpath("");

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
