package page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import untils.DriverUtils;

import java.time.Duration;

import static untils.DriverUtils.DRIVER;

public class ExternalTransferPage {
    private final By selectAccountSourceDropdownLocator = By.xpath("//label[text()='Chọn tài khoản']");
    private final By selectAccountSourceLocator = By.xpath("//li[text()='Chọn tài khoản']//following-sibling::li[1]");
    private final By receiverAccountTextBoxLocator = By.xpath("//td[label[text()='Số tài khoản nhận ']]//following-sibling::td//input");
    private final By receiverAccountNameTextBoxLocator = By.xpath("//td[label[text()='Tên tài khoản nhận ']]//following-sibling::td//input");
    private final By bankDropdownLocator = By.xpath("//label[text()='Chọn ngân hàng']");
    private final By selectBankLocator = By.xpath("//li[@data-label=Chọn ngân hàng]//following-sibling::li[1]");
    private final By branchDropdownLocator = By.xpath("//label[text()='Chọn chi nhánh']");
    private final By selectBranchLocator = By.xpath("//li[@data-label=\"Chọn chi nhánh\"]//following-sibling::li[1]");
    private final By InformationTransferTextBoxLocator = By.xpath("//td[label[text()='Nội dung chuyển tiền ']]//following-sibling::td//input");
    private final By AmountTransferTextBoxLocator = By.xpath("//td[label[text()='Số tiền chuyển khoản']]//following-sibling::td//input");
    private final By TransferButtonLocator = By.xpath("//td//input[@value=\"Chuyển tiền\"]");


    // td[label[text()= >> duplicate

    public void selectAccountSource() {
        DriverUtils.DRIVER.findElement(selectAccountSourceDropdownLocator).click();
        DriverUtils.DRIVER.findElement(selectAccountSourceLocator).click();
    }

    public void enterReceiverAccount(int account) {
        DriverUtils.DRIVER.findElement(receiverAccountTextBoxLocator).sendKeys(String.valueOf(account));
    }

    public void enterReceiverAccountNameTextBoxLocator(String name) {
        DriverUtils.DRIVER.findElement(receiverAccountNameTextBoxLocator).sendKeys(name);
    }

    public void selectBank() {
        DriverUtils.DRIVER.findElement(bankDropdownLocator).click();
        DriverUtils.DRIVER.findElement(selectBankLocator).click();
    }

    public void selectBranch() {
        // DriverUtils.DRIVER.findElement(branchDropdownLocator).click();
        // DriverUtils.DRIVER.findElement(selectBranchLocator).click();
        WebDriverWait wait = new WebDriverWait(DriverUtils.DRIVER, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(branchDropdownLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(selectBranchLocator)).click();
    }

    public void enterInformationTransfer(String inf) {
        DriverUtils.DRIVER.findElement(InformationTransferTextBoxLocator).sendKeys(inf);
    }

    public void enterAmount(Double amount) {
        DriverUtils.DRIVER.findElement(AmountTransferTextBoxLocator).sendKeys(String.valueOf(amount));
    }

    //TODO: Update void
    public void goToTransferInformationReviewPage() {
        DRIVER.findElement(TransferButtonLocator).click();
    }

    public void enterFormInformation(int account, String name, String inf, Double amount) {
        selectAccountSource();
        enterReceiverAccount(account);
        enterReceiverAccountNameTextBoxLocator(name);
        selectBank();

        selectBranch();
        enterInformationTransfer(inf);
        enterAmount(amount);
    }
}
