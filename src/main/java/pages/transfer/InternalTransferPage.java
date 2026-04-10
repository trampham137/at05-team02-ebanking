package pages.transfer;

import base.UserBasePage;
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

    public void clearReceiverAccount() {
        clear(receiverAccountTextboxLocator);
    }

    public void waitUntilReceiverNameLoaded() {
        waitVisible(receiverNameLocator);
        waitUntilTextNotEmpty(receiverNameLocator);
    }

    public void enterAmount(long amount) {
        type(amountTextboxLocator, String.valueOf(amount));
    }

    public void enterDescription(String description) {
        type(descriptionTextboxLocator, description);
    }

    public void fillTransferForm(InternalTransferData data) {
        selectSourceAccount(data.getSourceAccount());
        waitUntilAvailableBalanceLoaded();

        enterReceiverAccount(data.getReceiverAccount());
        click(amountTextboxLocator);
        waitUntilReceiverNameLoaded();

        enterAmount(data.getAmount());
        enterDescription(data.getDescription());
    }

    public TransferConfirmPage clickConfirm() {
        click(confirmButtonLocator);
        return new TransferConfirmPage(driver);
    }

    public boolean isStillOnTransferPage() {
        return isDisplayed(confirmButtonLocator);
    }

    public List<String> getToastMessages() {
        waitVisible(toastMessagesLocator);

        // List<String> messages = new ArrayList<>();
        // for (WebElement e : driver.findElements(toastMessagesLocator)) {
        //     messages.add(e.getText());
        // }
        // return messages;

        return driver.findElements(toastMessagesLocator)
                .stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public boolean isSourceAccountInvalid() {
        return getAttribute(sourceAccountDropdownLocator, "class").contains("ui-state-error");
    }

    public boolean isReceiverAccountInvalid() {
        return getAttribute(receiverAccountTextboxLocator, "class").contains("ui-state-error");
    }

    public boolean isAmountInvalid() {
        return getAttribute(amountTextboxLocator, "class").contains("ui-state-error");
    }

    public boolean isDescriptionInvalid() {
        return getAttribute(descriptionTextboxLocator, "class").contains("ui-state-error");
    }
}
