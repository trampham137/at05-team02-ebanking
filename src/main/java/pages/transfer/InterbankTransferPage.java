package pages.transfer;

import base.UserBasePage;
import models.InterbankTransferData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class InterbankTransferPage extends UserBasePage {
    public InterbankTransferPage(WebDriver driver) {
        super(driver);
    }

    private By dropdownOption(String optionText) {
        return By.xpath("//li[text()='" + optionText + "']");
    }

    private final By sourceAccountDropdownLocator = By.xpath("//td[label[text()='Tài khoản: ']]/following-sibling::td/div");
    private final By receiverAccountTextboxLocator = By.xpath("//td[label[text()='Số tài khoản nhận ']]/following-sibling::td/input");
    private final By receiverNameTextboxLocator = By.xpath("//td[label[text()='Tên tài khoản nhận ']]/following-sibling::td/input");
    private final By receiverBankNameDropdownLocator = By.xpath("//td[label[text()='Ngân hàng ']]/following-sibling::td/div");
    private final By receiverBranchNameDropdownLocator = By.xpath("//td[label[text()='Chi Nhánh ']]/following-sibling::td/div");
    // private final By receiverBranchNameDropdownLocator = By.xpath("//*[contains(@id, 'country_label')]");
    private final By amountTextboxLocator = By.xpath("//td[label[text()='Số tiền chuyển khoản']]/following-sibling::td/input");
    private final By descriptionTextboxLocator = By.xpath("//td[label[text()='Nội dung chuyển tiền ']]/following-sibling::td/input");
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Chuyển tiền']");
    private final By availableBalanceLocator = By.xpath("//td[label[text()='Số dư khả dụng']]/following-sibling::td/label");
    // private final By emptyAccountSourceMessageLocator = getMessLocatorByLabel("Chọn tài khoản");
    // private final By emptyReceiverAccountMessageLocator = getMessLocatorByLabel("Nhập số tài khoản");
    // private final By emptyReceiverAccountNameMessageLocator = getMessLocatorByLabel("Nhập tên người nhận");
    // private final By emptyBankMessageLocator = getMessLocatorByLabel("Mời chọn Ngân hàng");
    // private final By emptyBranchMessageLocator = getMessLocatorByLabel("Mời chọn chi nhánh");


    //  private By getMessLocatorByLabel(String mess) {
    //       return By.xpath(String.format(MessageLocator, mess));
    //  }

    public void selectSourceAccount(String sourceAccount) {
        click(sourceAccountDropdownLocator);
        click(dropdownOption(sourceAccount));
    }

    public void waitUntilAvailableBalanceLoaded() {
        waitVisible(availableBalanceLocator);
        waitUntilTextNotEmpty(availableBalanceLocator);
    }

    public void enterReceiverAccount(String receiverAccount) {
        type(receiverAccountTextboxLocator, receiverAccount);
    }

    public void enterReceiverName(String receiverName) {
        type(receiverNameTextboxLocator, receiverName);
    }

    public void selectReceiverBank(String bank) {
        click(receiverBankNameDropdownLocator);
        click(dropdownOption(bank));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // waitInVisible(dropdownOption(bank));
    }

    public void selectReceiverBranch(String branch) {
        click(receiverBranchNameDropdownLocator);
        click(dropdownOption(branch));
    }

    public void enterAmount(long amount) {
        type(amountTextboxLocator, String.valueOf(amount));
    }

    public void enterDescription(String description) {
        type(descriptionTextboxLocator, description);
    }

    public void fillTransferForm(InterbankTransferData data) {
        selectSourceAccount(data.getSourceAccount());
        waitUntilAvailableBalanceLoaded();

        enterReceiverAccount(data.getReceiverAccount());
        enterReceiverName(data.getReceiverName());
        selectReceiverBank(data.getBank());
        selectReceiverBranch(data.getBranch());
        enterAmount(data.getAmount());
        enterDescription(data.getDescription());
    }

    public TransferConfirmPage clickConfirm() {
        click(confirmButtonLocator);
        return new TransferConfirmPage(driver);
    }

    public TransferConfirmPage transfer(InterbankTransferData data) {
        fillTransferForm(data);
        return clickConfirm();
    }


}
