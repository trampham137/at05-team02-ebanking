package page;

import org.openqa.selenium.By;
import untils.DriverUtils;

public class TransferInformationReviewPage {
    private final By confirmButtonLocator = By.xpath("//td//input[@value=\"Xác nhận\"]");
    public void goToConfirmationPage(){
        DriverUtils.DRIVER.findElement(confirmButtonLocator).click();
    }
}
