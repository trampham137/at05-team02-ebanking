package pages.transfer;

import base.UserBasePage;
import io.qameta.allure.Step;
import models.InterbankTransferData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

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
    private final By amountTextboxLocator = By.xpath("//td[label[text()='Số tiền chuyển khoản']]/following-sibling::td/input");
    private final By descriptionTextboxLocator = By.xpath("//td[label[text()='Nội dung chuyển tiền ']]/following-sibling::td/input");
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Chuyển tiền']");
    private final By availableBalanceLocator = By.xpath("//td[label[text()='Số dư khả dụng']]/following-sibling::td/label");

    private final By toastMessagesLocator = By.xpath("//div[@id='j_idt8:messages_container']//span[@class='ui-growl-title']");

    @Step("Select source account: {sourceAccount}")
    public void selectSourceAccount(String sourceAccount) {
        click(sourceAccountDropdownLocator);
        click(dropdownOption(sourceAccount));
    }

    @Step("Wait until available balance loaded")
    public void waitUntilAvailableBalanceLoaded() {
        waitVisible(availableBalanceLocator);
        waitUntilTextNotEmpty(availableBalanceLocator);
    }

    @Step("Enter receiver account: {receiverAccount}")
    public void enterReceiverAccount(String receiverAccount) {
        type(receiverAccountTextboxLocator, receiverAccount);
    }

    @Step("Clear receiver account")
    public void clearReceiverAccount() {
        clear(receiverAccountTextboxLocator);
    }

    @Step("Enter receiver name: {receiverName}")
    public void enterReceiverName(String receiverName) {
        type(receiverNameTextboxLocator, receiverName);
    }

    @Step("Select receiver bank: {bank}")
    public void selectReceiverBank(String bank) {
        click(receiverBankNameDropdownLocator);
        click(dropdownOption(bank));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Select receiver branch: {branch}")
    public void selectReceiverBranch(String branch) {
        click(receiverBranchNameDropdownLocator);
        click(dropdownOption(branch));
    }

    @Step("Enter transfer amount: {amount}")
    public void enterAmount(long amount) {
        type(amountTextboxLocator, String.valueOf(amount));
    }

    @Step("Enter transfer description: {description}")
    public void enterDescription(String description) {
        type(descriptionTextboxLocator, description);
    }

    @Step("Fill interbank transfer form")
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

    @Step("Click transfer confirm button")
    public TransferConfirmPage clickConfirm() {
        click(confirmButtonLocator);
        return new TransferConfirmPage(driver);
    }

    @Step("Perform interbank transfer")
    public TransferConfirmPage transfer(InterbankTransferData data) {
        fillTransferForm(data);
        return clickConfirm();
    }

    @Step("Check still on transfer page")
    public boolean isStillOnTransferPage() {
        return isDisplayed(confirmButtonLocator);
    }

    @Step("Get toast messages")
    public List<String> getToastMessages() {
        waitVisible(toastMessagesLocator);

        return driver.findElements(toastMessagesLocator)
                .stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    @Step("Check source account field invalid")
    public boolean isSourceAccountInvalid() {
        return getAttribute(sourceAccountDropdownLocator, "class").contains("ui-state-error");
    }

    @Step("Check receiver account field invalid")
    public boolean isReceiverAccountInvalid() {
        return getAttribute(receiverAccountTextboxLocator, "class").contains("ui-state-error");
    }

    @Step("Check receiver name field invalid")
    public boolean isReceiverNameInvalid() {
        return getAttribute(receiverNameTextboxLocator, "class").contains("ui-state-error");
    }

    @Step("Check bank field invalid")
    public boolean isBankInvalid() {
        return getAttribute(receiverBankNameDropdownLocator, "class").contains("ui-state-error");
    }

    @Step("Check branch field invalid")
    public boolean isBranchInvalid() {
        return getAttribute(receiverBranchNameDropdownLocator, "class").contains("ui-state-error");
    }

    @Step("Check amount field invalid")
    public boolean isAmountInvalid() {
        return getAttribute(amountTextboxLocator, "class").contains("ui-state-error");
    }

    @Step("Check description field invalid")
    public boolean isDescriptionInvalid() {
        return getAttribute(descriptionTextboxLocator, "class").contains("ui-state-error");
    }
}
