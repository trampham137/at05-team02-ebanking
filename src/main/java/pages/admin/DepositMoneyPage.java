package pages.admin;

import base.AdminBasePage;
import models.DepositData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DepositMoneyPage extends AdminBasePage {
    private By inputByLabel(String label) {
        return By.xpath("//td[normalize-space()='" + label + "']/following-sibling::td[2]//input");
    }

    private final By receiverAccountTextboxLocator = inputByLabel("Tài khoản nhận");
    private final By amountTextboxLocator = inputByLabel("Số tiền");
    private final By descriptionTextboxLocator = inputByLabel("Nội dung thanh toán");

    private final By confirmButtonLocator = By.xpath("//input[@type='submit' and @value='Xác nhận']");
    private final By successMessageLocator = By.xpath("//td/label[text()='nộp tiền thành công']");

    public DepositMoneyPage(WebDriver driver) {
        super(driver);
    }

    public void depositToAccount(DepositData data) {
        type(receiverAccountTextboxLocator, data.getReceiverAccount());
        type(amountTextboxLocator, data.getAmount());
        type(descriptionTextboxLocator, data.getDescription());
        click(confirmButtonLocator);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isDepositSuccessful() {
        return isDisplayed(successMessageLocator);
    }

    public String getSuccessMessage() {
        return getText(successMessageLocator).trim();
    }
}
