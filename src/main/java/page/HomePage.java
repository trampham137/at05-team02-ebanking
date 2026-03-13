package page;

import org.openqa.selenium.By;
import untils.DriverUtils;

public class HomePage {
    private final By personalInformationLocator = By.xpath("//a[span[text()='Thông tin cá nhân']]");
    private final By accountLocator = By.xpath("//a[span[text()='Tài khoản']]");
    private final By openAccountLocator = By.xpath("//a[span[text()='Mở tài khoản']]");
    private final By changePassWordLocator = By.xpath("//a[span[text()='Đổi mật khẩu']]");
    private final By transactionHistorylocator = By.xpath("//a[span[text()='Nhật kí giao dịch']]");
    private final By internalTransferLocator = By.xpath("//a[span[text()='Chuyển  khoản']]");
    private final By externalTransferLocator = By.xpath("//a[span[text()='Liên Ngân Hàng']]");
    private final By logoutLocator = By.xpath("//a[span[text()='Đăng xuất']]");

    // a[span[text() >> duplicate


    public void goToPersonalInformationPage(){
        DriverUtils.DRIVER.findElement(personalInformationLocator).click();
    }
    public void goToAccountPage(){
        DriverUtils.DRIVER.findElement(accountLocator).click();
    }
    public void goToOpenAccountPage(){
        DriverUtils.DRIVER.findElement(openAccountLocator).click();
    }
    public void goToChangePasswordPage(){
        DriverUtils.DRIVER.findElement(changePassWordLocator).click();
    }
    public void goToTransactionHistoryPage(){
        DriverUtils.DRIVER.findElement(transactionHistorylocator).click();
    }
    public void goToInternalTransferPage(){
        DriverUtils.DRIVER.findElement(internalTransferLocator).click();
    }
    public void goToExternalTransfer(){
        DriverUtils.DRIVER.findElement(externalTransferLocator).click();
    }
    public void goToLogoutPage(){
        DriverUtils.DRIVER.findElement(logoutLocator).click();
    }


}
