package page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterPage {
    private final By createAccountButtonLocator = By.partialLinkText("Tạo tài khoản");
    private final String textBoxLocator = "//td[label[text()='%s']]//following-sibling::td//input";
    //  private final By accountTextBoxLocator = By.xpath(String.format(textBoxLocator,"Tài khoản "));
    //  private final By passwordTextBoxLocator = By.xpath(String.format(textBoxLocator,"Mật khẩu: *"));
    //  private final By confirmPasswordTextBoxLocator = By.xpath(String.format(textBoxLocator,"Nhập lại mật khẩu: *"));
    //  private final By nameTextBoxLocator = By.xpath(String.format(textBoxLocator,"Họ tên "));
    //  private final By phoneTextBoxLocator = By.xpath(String.format(textBoxLocator, "Số điện thoại "));
    //  private final By dobTextBoxLocator = By.xpath(String.format(textBoxLocator,"Ngày sinh:"));
    //  private final By genderRadioButtonLocator = By.xpath(String.format(textBoxLocator,"Giới tính:"));
    //   private final By cityDropdownLocator = By.xpath(String.format(textBoxLocator,"Tỉnh/Thành Phố"));
    //  private final By CMNDTextBoxLocator = By.xpath(String.format(textBoxLocator,"Số CMND: "));
    //   private final By emailTextBoxLocator = By.xpath(String.format(textBoxLocator,"Emaiḷ: "));
    private final By accountTextBoxLocator = getTextBoxLabelLocator("Tài khoản ");
    private final By passwordTextBoxLocator = getTextBoxLabelLocator("Mật khẩu: *");
    private final By confirmPasswordTextBoxLocator = getTextBoxLabelLocator("Nhập lại mật khẩu: *");
    private final By nameTextBoxLocator = getTextBoxLabelLocator("Họ tên ");
    private final By phoneTextBoxLocator = getTextBoxLabelLocator("Số điện thoại ");
    private final By dobTextBoxLocator = getTextBoxLabelLocator("Ngày sinh:");
    private final By maleGenderRadioButtonLocator = By.xpath("//td[following-sibling::td[label[text()='Nam']]]/div");
    private final By femaleGenderRadioButtonLocator = By.xpath("(//div[contains(@class,'ui-radiobutton-box ui-widget ui-corner-all ui-state-default')])[2]");
    private final By cityDropdownLocator = By.xpath("//label[text()='Thành phố']");

    private final String citySelectLocator = "//li[contains(text(),'%s')]";

    private final By CMNDTextBoxLocator = getTextBoxLabelLocator("Số CMND: ");
    private final By emailTextBoxLocator = getTextBoxLabelLocator("Emaiḷ: ");
    private final By registerButtonLocator = By.xpath("//span[text()='Đăng ký']");


    public By getTextBoxLabelLocator(String label) {
        return By.xpath(String.format(textBoxLocator, label));
    }

    public void clickCreateAccountButton() {
        DriverUtils.DRIVER.findElement(createAccountButtonLocator).click();
    }

    public void enterAccount(int acc) {
        DriverUtils.DRIVER.findElement(accountTextBoxLocator).sendKeys(String.valueOf(acc));
    }

    public void enterPassword(String password) {
        DriverUtils.DRIVER.findElement(passwordTextBoxLocator).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmpass) {
        DriverUtils.DRIVER.findElement(confirmPasswordTextBoxLocator).sendKeys(confirmpass);
    }

    public void enterName(String name) {
        DriverUtils.DRIVER.findElement(nameTextBoxLocator).sendKeys(name);
    }

    public void enterPhone(int phone) {
        DriverUtils.DRIVER.findElement(phoneTextBoxLocator).sendKeys(String.valueOf(phone));
    }

    public void enterDOB(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dob = date.format(formatter);
        DriverUtils.DRIVER.findElement(dobTextBoxLocator).sendKeys(dob);
    }

    public void clickGenderMale() {
        DriverUtils.DRIVER.findElement(maleGenderRadioButtonLocator).click();
    }

    public void clickGenderFemale() {
        DriverUtils.DRIVER.findElement(femaleGenderRadioButtonLocator).click();
    }

    public void selectGender(String gender) {
        if (gender.equals("Male")) {
            clickGenderMale();
        } else if (gender.equals("Female")) {
            clickGenderFemale();
        }
    }

    public void enterCMND(int number) {
        DriverUtils.DRIVER.findElement(CMNDTextBoxLocator).sendKeys(String.valueOf(number));
    }

    public void enterEmail(String email) {
        DriverUtils.DRIVER.findElement(emailTextBoxLocator).sendKeys(email);
    }

    public By getSelectCityLocator(String city) {
        return By.xpath(String.format(citySelectLocator, city));
    }

    public void selectCity(String city) {
        //  DriverUtils.DRIVER.findElement(cityDropdownLocator).click();

        //  DriverUtils.DRIVER.findElement(getSelectCityLocator(city)).click();
        WebDriverWait wait = new WebDriverWait(DriverUtils.DRIVER, Duration.ofSeconds(10));

        // mở dropdown thành phố
        wait.until(ExpectedConditions.elementToBeClickable(cityDropdownLocator)).click();

        // chọn thành phố
        wait.until(ExpectedConditions.elementToBeClickable(getSelectCityLocator(city))).click();
    }

    public void select() {
        DriverUtils.DRIVER.findElement(cityDropdownLocator).click();
    }

    public void enterRegisterForm(int acc, String password, String confirmPassword, String name, int phone, LocalDate date, String gender, String city, int CMND, String email) {
        enterAccount(acc);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterName(name);
        enterPhone(phone);
        enterDOB(date);
        selectGender(gender);
        selectCity(city);
        enterCMND(CMND);
        enterEmail(email);
    }

    public void clickRegisterButton() {
        DriverUtils.DRIVER.findElement(registerButtonLocator).click();
    }
}
