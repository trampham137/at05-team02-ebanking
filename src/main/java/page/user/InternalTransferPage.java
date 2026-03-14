package page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.base.BasePage;


import java.time.Duration;

import static utils.DriverUtils.DRIVER;

public class InternalTransferPage extends BasePage {

    private final By selectAccountSource = getDropdownLabelLocator("Chọn tài khoản");
    private final By receiverAccountTextBoxLocator = getItemTextBoxLocator("Tài khoản nhận");
    private final By amountTextBoxLocator = getItemTextBoxLocator("Số tiền");
    private final By paymentDescriptionTextBoxLocator = getItemTextBoxLocator("Nội dung thanh toán");
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");

    public void selelectAccountSource(String acc) {
        WebDriverWait wait = new WebDriverWait(DRIVER, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(selectAccountSource)).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDropdownItemLocator(acc))).click();
    }

    public void enterReceiverAccount(int account) {
        DRIVER.findElement(receiverAccountTextBoxLocator).sendKeys(String.valueOf(account));
    }

    public void enterAmount(double amount) {
        DRIVER.findElement(amountTextBoxLocator).sendKeys(String.valueOf(amount));
    }

    public void enterPaymentDescription(String desc) {
        DRIVER.findElement(paymentDescriptionTextBoxLocator).sendKeys(desc);
    }

    public void goToTransferInformationReviewPage() {
        DRIVER.findElement(confirmButtonLocator).click();
    }

    // TODO: Them ... luu bien
    public void enterTransferInformation(String acc, int account, double amount, String desc) {
        selelectAccountSource(acc);
        enterReceiverAccount(account);
        enterAmount(amount);
        enterPaymentDescription(desc);
    }


}
