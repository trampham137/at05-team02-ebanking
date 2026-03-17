package page.admin;

import base.BaseAdminPage;
import org.openqa.selenium.By;
import utils.DriverUtils;

public class DepositPage extends BaseAdminPage {
    //TODO: tao 2 package : admin , user
    private final By receiverAccountTextBoxLocator = By.name("j_idt23:j_idt27");
    private final By amountTextBoxLocator = By.name("j_idt23:j_idt29");
    private final By informationTransferTextBoxLocator = By.name("j_idt23:j_idt31");
    private final By confirmButtonLocator = By.xpath("//input[@value='Xác nhận']");

    public void enterReceiverAccount(int receiver) {
        DriverUtils.DRIVER.findElement(receiverAccountTextBoxLocator).sendKeys(String.valueOf(receiver));
    }

    public void enterAmount(double amount) {
        DriverUtils.DRIVER.findElement(amountTextBoxLocator).sendKeys(String.valueOf(amount));
    }

    public void enterPaymentDescription(String desc) {
        DriverUtils.DRIVER.findElement(informationTransferTextBoxLocator).sendKeys(desc);
    }

    public void clickConfirmButton() {
        DriverUtils.DRIVER.findElement(confirmButtonLocator).click();
    }

    public void enterDepositForm(int receiver, double amount, String desc) {
        enterReceiverAccount(receiver);
        enterAmount(amount);
        enterPaymentDescription(desc);
    }
}
