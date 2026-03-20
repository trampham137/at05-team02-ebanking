package pages.transfer.internal;

import org.openqa.selenium.By;
import utils.DriverUtils;

import static utils.WaitUtils.waitForElementClickable;

public class InternalTransferConfirmPage {
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");


    public InternalTransferOtpPage goToInternalTransferOTPPage() {
        waitForElementClickable(confirmButtonLocator);
        DriverUtils.getDriver().findElement(confirmButtonLocator).click();
        return new InternalTransferOtpPage();
    }
}
