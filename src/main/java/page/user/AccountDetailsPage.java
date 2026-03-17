package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class AccountDetailsPage {
    private final By accountBalanceLabelLocator = By.xpath("//label[text()='Số dư tài khoản']");
    private final By accountBalanceAndCurrencyValueLocator = By.xpath("//td[label[text()='Số dư tài khoản']]//following-sibling::td//label");

    public boolean isAccountBalanceLabelDisplayed() {
        return DriverUtils.DRIVER.findElement(accountBalanceLabelLocator).isDisplayed();
    }

    public String getAccountBalanceAndCurrency() {
        return DriverUtils.DRIVER.findElement(accountBalanceAndCurrencyValueLocator).getText();
    }

    public int getAccountBalanceValue() {
        String AccountBalanceAndCurrencyText = getAccountBalanceAndCurrency();
        String balance = AccountBalanceAndCurrencyText.split(" ")[0].replace(",", "");
        return Integer.parseInt(balance);
    }

    public String getCurrencyValue() {
        String AccountBalanceAndCurrencyText = getAccountBalanceAndCurrency();
        return AccountBalanceAndCurrencyText.split(" ")[1];
    }
}
