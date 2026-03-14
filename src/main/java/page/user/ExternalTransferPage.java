package page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverUtils;

import java.time.Duration;

import static utils.DriverUtils.DRIVER;

public class ExternalTransferPage {

    private final By transferButtonLocator = By.xpath("//td//input[@value='Chuyển tiền']");
    // dinh nghia 1 trang cho ke thua lai dropdown
    private final String dynamicDropdownLabelLocator = "//label[text()='%s']";
    // TODO: khong dung contain
    private final String dynamicDropdownItemLocator = "//li[contains(@data-label,'%s')]";
    private final String dynamicTextBoxLocator = "//td[label[text()='%s']]//following-sibling::td//input";

    private By getDropdownLabelLocator(String label) {
        return By.xpath(String.format(dynamicDropdownLabelLocator, label));
    }

    private By getDropdownItemLocator(String item) {
        return By.xpath(String.format(dynamicDropdownItemLocator, item));
    }

    private By getItemTextBoxLocator(String label) {
        return By.xpath(String.format(dynamicTextBoxLocator, label));
    }

    private final By selectAccountSourceDropdownLocator = getDropdownLabelLocator("Chọn tài khoản");
    private final By bankDropdownLocator = getDropdownLabelLocator("Chọn ngân hàng");
    private final By branchDropdownLocator = getDropdownLabelLocator("Chọn chi nhánh");
    private final By receiverAccountTextBoxLocator = getItemTextBoxLocator("Số tài khoản nhận ");
    private final By receiverAccountNameTextBoxLocator = getItemTextBoxLocator("Tên tài khoản nhận ");
    private final By transferInformationTextboxLocator = getItemTextBoxLocator("Nội dung chuyển tiền ");
    private final By transferAmountTextboxLocator = getItemTextBoxLocator("Số tiền chuyển khoản");


    public void selectAccountSource(String account) {
        DriverUtils.DRIVER.findElement(selectAccountSourceDropdownLocator).click();
        DriverUtils.DRIVER.findElement(getDropdownItemLocator(account)).click();
    }

    public void enterReceiverAccount(int receiveraccount) {
        DriverUtils.DRIVER.findElement(receiverAccountTextBoxLocator).sendKeys(String.valueOf(receiveraccount));
    }

    public void enterReceiverAccountName(String name) {
        DriverUtils.DRIVER.findElement(receiverAccountNameTextBoxLocator).sendKeys(name);
    }

    public void selectBank(String bank) {
        DriverUtils.DRIVER.findElement(bankDropdownLocator).click();
        DriverUtils.DRIVER.findElement(getDropdownItemLocator(bank)).click();
    }

    // TODO: enum thay vi string
    public void selectBranch(String branch) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.DRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(branchDropdownLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDropdownItemLocator(branch))).click();
    }

    public void enterInformationTransfer(String inf) {
        DriverUtils.DRIVER.findElement(transferInformationTextboxLocator).sendKeys(inf);
    }

    public void enterAmount(Double amount) {
        DriverUtils.DRIVER.findElement(transferAmountTextboxLocator).sendKeys(String.valueOf(amount));
    }

    //TODO: Update name void
    public void goToTransferInformationReviewPage() {
        DRIVER.findElement(transferButtonLocator).click();
    }

    // TODO: them ... luu 7 bien
    public void enterInformation(String account, int receiveraccount, String name, String bank, String branch, String inf, Double amount) {
        selectAccountSource(account);
        enterReceiverAccount(receiveraccount);
        enterReceiverAccountName(name);
        selectBank(bank);

        selectBranch(branch);
        enterInformationTransfer(inf);
        enterAmount(amount);
    }
}
