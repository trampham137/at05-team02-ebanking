package pages.transfer.interbank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;
import enums.Bank;
import enums.Branch;
import utils.DriverUtils;

import java.time.Duration;

public class InterbankTransferPage extends BasePage {
    private final By sourceAccountDropdownLocator = By.xpath("");
    private final By availableBalanceValueLocator = By.xpath("");
    private final By receiverAccountTextboxLocator = By.xpath("");


    private final By bankDropdownLocator = getDropdownLabelLocator("Chọn ngân hàng");
    private final By branchDropdownLocator = getDropdownLabelLocator("Chọn chi nhánh");

    private final By receiverAccountNameTextBoxLocator = getItemTextBoxLocator("Tên tài khoản nhận ");
    private final By transferInformationTextboxLocator = getItemTextBoxLocator("Nội dung chuyển tiền ");
    private final By transferAmountTextboxLocator = getItemTextBoxLocator("Số tiền chuyển khoản");
    private final By transferButtonLocator = By.xpath("//td//input[@value='Chuyển tiền']");

    public InterbankTransferPage(WebDriver driver) {
        super(driver);
    }

    // public void selectAccountSource(int account) {
    //     DriverUtils.DRIVER.findElement(selectAccountSourceDropdownLocator).click();
    //     DriverUtils.DRIVER.findElement(getDropdownItemLocator(String.valueOf(account))).click();
    // }
    //
    // public void enterReceiverAccount(int receiveraccount) {
    //     DriverUtils.DRIVER.findElement(receiverAccountTextBoxLocator).sendKeys(String.valueOf(receiveraccount));
    // }
    //
    // public void enterReceiverAccountName(String name) {
    //     DriverUtils.DRIVER.findElement(receiverAccountNameTextBoxLocator).sendKeys(name);
    // }
    //
    // public void selectBank(Bank bank) {
    //     DriverUtils.DRIVER.findElement(bankDropdownLocator).click();
    //     DriverUtils.DRIVER.findElement(getDropdownItemLocator(bank.getValue())).click();
    // }
    //
    // // TODO: enum thay vi string
    // public void selectBranch(Branch branch) {
    //     WebDriverWait wait = new WebDriverWait(DriverUtils.DRIVER, Duration.ofSeconds(10));
    //     wait.until(ExpectedConditions.elementToBeClickable(branchDropdownLocator)).click();
    //     wait.until(ExpectedConditions.elementToBeClickable(getDropdownItemLocator(branch.getValue()))).click();
    // }
    //
    // public void enterTransferDescription(String inf) {
    //     DriverUtils.DRIVER.findElement(transferInformationTextboxLocator).sendKeys(inf);
    // }
    //
    // public void enterAmount(Double amount) {
    //     DriverUtils.DRIVER.findElement(transferAmountTextboxLocator).sendKeys(String.valueOf(amount));
    // }
    //
    // // TODO: Update name void
    // public void goToTransferInformationReviewPage() {
    //     DRIVER.findElement(transferButtonLocator).click();
    // }
    //
    // // TODO: them ... luu 7 bien
    // public void enterInformation(ExternalTransferInfo info) {
    //     selectAccountSource(info.sourceAccount);
    //     enterReceiverAccount(info.receiverAccount);
    //     enterReceiverAccountName(info.receiverName);
    //     selectBank(info.bank);
    //     selectBranch(info.branch);
    //     enterTransferDescription(info.transferDescription);
    //     enterAmount(info.amount);
    // }
}
