package page;

import org.openqa.selenium.By;


import static untils.DriverUtils.DRIVER;

public class InternalTransferPage {
    private final By selectAccountSourceDropdownLocator = By.xpath("//td[label[text()='Tài khoản nguồn']]//following-sibling::td//label");
    private final By selectAccountSourceLocator = By.xpath("//div//li[text()='Chọn tài khoản']//following-sibling::li[1]");
    private final By receiverAccountTextBoxLocator = By.xpath("//td[label[text()='Tài khoản nhận']]//following-sibling::td//input");
    private final By amountTextBoxLocator = By.xpath("//td[label[text()='Số tiền']]//following-sibling::td//input");
    private final By paymentDescriptionTextBoxLocator = By.xpath("//td[label[text()='Nội dung thanh toán']]//following-sibling::td//input");
    private final By confirmButtonlocator = By.xpath("//td//input[@value=\"Xác nhận\"]");

    // >> reused with external

    public void selelectAccountSource(){
        DRIVER.findElement(selectAccountSourceDropdownLocator).click();
        DRIVER.findElement(selectAccountSourceLocator).click();
    }
    public void enterReceiverAccount(int account){
        DRIVER.findElement(receiverAccountTextBoxLocator).sendKeys(String.valueOf(account));
    }
    public void enterAmount( double amount){
        DRIVER.findElement(amountTextBoxLocator).sendKeys(String.valueOf(amount));
    }
    public void enterPaymentDescription(String desc){
        DRIVER.findElement(paymentDescriptionTextBoxLocator).sendKeys(desc);
    }
    public void goToTransferInformationReviewPage(){
        DRIVER.findElement(confirmButtonlocator).click();
    }
    public void enterTransferInformation(int account,double amount,String desc){
        selelectAccountSource();
        enterReceiverAccount(account);
        enterAmount(amount);
        enterPaymentDescription(desc);
    }


}
