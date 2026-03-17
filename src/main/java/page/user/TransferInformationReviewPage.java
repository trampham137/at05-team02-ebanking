package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class TransferInformationReviewPage {
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");
    public void goToConfirmationPage(){
        DriverUtils.DRIVER.findElement(confirmButtonLocator).click();
    }
}
