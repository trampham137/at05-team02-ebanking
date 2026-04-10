package pages.transfer;

import base.UserBasePage;
import io.qameta.allure.Step;
import models.InternalTransferData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class InternalTransferPage extends UserBasePage {
    public InternalTransferPage(WebDriver driver) {
        super(driver);
    }

    private By dropdownOption(String optionText) {
        return By.xpath("//li[normalize-space()='" + optionText + "']");
    }

    private final By sourceAccountDropdownLocator = By.xpath("//td[label[text()='Tài khoản nguồn']]/following-sibling::td/div");
    private final By receiverAccountTextboxLocator = By.xpath("//td[label[text()='Tài khoản nhận']]/following-sibling::td/input");
    private final By amountTextboxLocator = By.xpath("//td[label[text()='Số tiền']]/following-sibling::td/input");
    private final By descriptionTextboxLocator = By.xpath("//td[label[text()='Nội dung thanh toán']]/following-sibling::td/input");
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");

    private final By availableBalanceLocator = By.xpath("//td[label[text()='Số dư khả dụng']]/following-sibling::td/label");
    private final By receiverNameLocator = By.xpath("//td[label[text()='Tên người nhận']]/following-sibling::td/label");

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

    @Step("Wait until receiver name loaded")
    public void waitUntilReceiverNameLoaded() {
        waitVisible(receiverNameLocator);
        waitUntilTextNotEmpty(receiverNameLocator);
    }

    @Step("Enter transfer amount: {amount}")
    public void enterAmount(long amount) {
        type(amountTextboxLocator, String.valueOf(amount));
    }

    @Step("Enter transfer description: {description}")
    public void enterDescription(String description) {
        type(descriptionTextboxLocator, description);
    }

    @Step("Fill internal transfer form")
    public void fillTransferForm(InternalTransferData data) {
        selectSourceAccount(data.getSourceAccount());
        waitUntilAvailableBalanceLoaded();

        enterReceiverAccount(data.getReceiverAccount());
        click(amountTextboxLocator);
        waitUntilReceiverNameLoaded();

        enterAmount(data.getAmount());
        enterDescription(data.getDescription());
    }

    @Step("Click transfer confirm button")
    public TransferConfirmPage clickConfirm() {
        click(confirmButtonLocator);
        return new TransferConfirmPage(driver);
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

    @Step("Check amount field invalid")
    public boolean isAmountInvalid() {
        return getAttribute(amountTextboxLocator, "class").contains("ui-state-error");
    }

    @Step("Check description field invalid")
    public boolean isDescriptionInvalid() {
        return getAttribute(descriptionTextboxLocator, "class").contains("ui-state-error");
    }
}
