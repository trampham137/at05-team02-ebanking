package pages.account;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDetailPage extends BasePage {
    private final By accountNameValueLocator = By.xpath("//td[.//label[text()='Tên tài khoản:']]/following-sibling::td");
    private final By accountTypeValueLocator = By.xpath("//td[.//label[text()='Loại tài khoản:']]/following-sibling::td");
    private final By balanceValueLocator = By.xpath("//td[.//label[text()='Số dư tài khoản']]/following-sibling::td");

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
