package pages.transfer.internal;

import org.openqa.selenium.By;
import utils.DriverUtils;

// TODO: update name
public class TransferInformationReviewPage {
    private final By confirmButtonLocator = By.xpath("//input[@value='Xác nhận']");

    public void goToConfirmationPage() {
        // DriverUtils.DRIVER.findElement(confirmButtonLocator).click();
    }
}
