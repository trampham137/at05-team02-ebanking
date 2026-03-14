package page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static utils.DriverUtils.DRIVER;

public class InternalTransferPage {
    // TODO: Dinh nghia 1 trang cho ke thua lai (trung voi dynamicDropdownLabelLocator ben external)
    private final By selectAccountSource = By.xpath("//label[text()='Chọn tài khoản']");
    private final String dynamicLocator = "//td[label[text()='%s']]//following-sibling::td//input";

    private By getItemLocator(String label) {
        return By.xpath(String.format(dynamicLocator, label));
    }

    private final By receiverAccountTextBoxLocator = getItemLocator("Tài khoản nhận");
    private final By amountTextBoxLocator = getItemLocator("Số tiền");
    private final By paymentDescriptionTextBoxLocator = getItemLocator("Nội dung thanh toán");
    // TODO: Dinh nghia 1 trang cho ke thua lai
    private final String accountItemLocator = "//li[contains(@data-label,'%s')]";
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");

    // >> reused with external

    private By getLocatorByAccount(String acc) {
        return By.xpath(String.format(accountItemLocator, acc));
    }

    public void selelectAccountSource(String acc) {
        WebDriverWait wait = new WebDriverWait(DRIVER, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(selectAccountSource)).click();
        wait.until(ExpectedConditions.elementToBeClickable(getLocatorByAccount(acc))).click();
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
