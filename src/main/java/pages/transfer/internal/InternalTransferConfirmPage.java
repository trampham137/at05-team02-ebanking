package pages.transfer.internal;

import base.UserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InternalTransferConfirmPage extends UserBasePage {
    private By valueByLabel(String label) {
        return By.xpath("//td[label[text()='" + label + "']]/following-sibling::td/label");
    }

    private final By sourceAccountTextLocator = valueByLabel("Tài khoản gửi");
    private final By targetAccountTextLocator = valueByLabel("Tài khoản nhận");
    private final By amountTextLocator = valueByLabel("Số tiền chuyển khoản");
    private final By confirmButtonLocator = By.cssSelector("input[type=submit]");

    public InternalTransferConfirmPage(WebDriver driver) {
        super(driver);
    }

    public String getSourceAccount() {
        return getText(sourceAccountTextLocator).trim();
    }

    public String getTargetAccount() {
        return getText(targetAccountTextLocator).trim();
    }

    public long getAmount() {
        return parseCurrencyToLong(getText(amountTextLocator));
    }

    public InternalTransferOtpPage clickConfirm() {
        click(confirmButtonLocator);
        return new InternalTransferOtpPage(driver);
    }
}
