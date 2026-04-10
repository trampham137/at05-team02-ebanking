package pages.transfer;

import base.UserBasePage;
import io.qameta.allure.Step;
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

    @Step("Get confirm source account")
    public String getSourceAccount() {
        return getText(sourceAccountTextLocator).trim();
    }

    @Step("Get confirm target account")
    public String getTargetAccount() {
        return getText(targetAccountTextLocator).trim();
    }

    @Step("Get confirm transfer amount")
    public long getAmount() {
        return parseCurrencyToLong(getText(amountTextLocator));
    }

    @Step("Get confirm transfer description")
    public String getDescription() {
        return getText(descriptionTextLocator);
    }

    @Step("Get confirm transfer data")
    public InternalTransferData getTransferData() {
        return new InternalTransferData(
                getSourceAccount(),
                getTargetAccount(),
                getAmount(),
                getDescription()
        );
    }

    @Step("Click confirm transfer button")
    public TransferOtpPage clickConfirm() {
        click(confirmButtonLocator);
        return new TransferOtpPage(driver);
    }
}
