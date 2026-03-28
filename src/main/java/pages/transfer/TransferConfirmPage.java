package pages.transfer;

import base.UserBasePage;
import models.InternalTransferData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferConfirmPage extends UserBasePage {
    private By valueByLabel(String label) {
        return By.xpath("//td[label[text()='" + label + "']]/following-sibling::td/label");
    }

    private final By sourceAccountTextLocator = valueByLabel("Tài khoản gửi");
    private final By targetAccountTextLocator = valueByLabel("Tài khoản nhận");
    private final By amountTextLocator = valueByLabel("Số tiền chuyển khoản");
    private final By descriptionTextLocator = valueByLabel("Nội dung chuyển khoản");
    private final By confirmButtonLocator = By.cssSelector("input[type=submit]");

    public TransferConfirmPage(WebDriver driver) {
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

    public String getDescription() {
        return getText(descriptionTextLocator);
    }

    public InternalTransferData getTransferData() {
        return new InternalTransferData(
                getSourceAccount(),
                getTargetAccount(),
                getAmount(),
                getDescription()
        );
    }

    public TransferOtpPage clickConfirm() {
        click(confirmButtonLocator);
        return new TransferOtpPage(driver);
    }
}
