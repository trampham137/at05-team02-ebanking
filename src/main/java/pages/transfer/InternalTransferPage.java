package pages.transfer;

import base.UserBasePage;
import models.InternalTransferData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    public InternalTransferPage clickConfirmButtonExpectingError() {
        click(confirmButtonLocator);
        return this;
    }

    public void clearReceiverAccount() {
        clear(receiverAccountTextboxLocator);
    }

    public By getRequiredMessageByText(String text) {
        return By.xpath("//span[text()='" + text + "']");
    }

    public boolean isSourceAccountRequiredMessageDisplayed() {
        return isDisplayed(getRequiredMessageByText("Mời chọn tài khoản"));
    }

    public boolean isAmountRequiredMessageDisplayed() {
        return isDisplayed(getRequiredMessageByText("Nhập số tiền"));
    }

    public boolean isContentRequiredMessageDisplayed() {
        return isDisplayed(getRequiredMessageByText("Nhập nội dung"));
    }

    public boolean isAmountGreaterThanBalanceMessageDisplayed() {
        return isDisplayed(getRequiredMessageByText("Số tiền vượt mức"));
    }

}
