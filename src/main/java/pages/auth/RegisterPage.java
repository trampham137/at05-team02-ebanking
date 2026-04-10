package pages.auth;

import base.UserBasePage;
import io.qameta.allure.Step;
import models.RegisterData;
import models.enums.City;
import models.enums.Gender;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends UserBasePage {
    private final By usernameTextboxLocator = By.name("j_idt9:soucre");
    private final By passwordTextboxLocator = By.name("j_idt9:pwd1");
    private final By confirmPasswordTextboxLocator = By.name("j_idt9:pwd2");
    private final By fullNameTextboxLocator = By.name("j_idt9:key");
    private final By phoneTextboxLocator = By.name("j_idt9:phone");
    private final By dobTextboxLocator = By.name("j_idt9:mask_input");
    private final By cityDropdownLocator = By.xpath("//div[@id='j_idt9:country']");
    private final By idNumberTextboxLocator = By.name("j_idt9:cmnd");
    private final By emailTextboxLocator = By.name("j_idt9:email");
    private final By submitButton = By.xpath("//button[.//span[text()='Đăng ký']]");
    private final By confirmButton = By.xpath("//input[@type='submit']");

    private final By closeSuccessPopupLocator = By.cssSelector("#primefacesmessagedlg .ui-dialog-titlebar-close");

    private By getGenderLocator(Gender gender) {
        return By.xpath("//td//*[text()='" + gender.getDisplayName() + "']");
    }

    private By cityOptionLocator(City city) {
        return By.xpath(String.format("//div[@id='j_idt9:country_panel']//li[text()='%s']", city.getDisplayName()));
    }

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Register new user with username: {data.username}, email: {data.email}")
    public void register(RegisterData data) {
        type(usernameTextboxLocator, data.getUsername());
        type(passwordTextboxLocator, data.getPassword());
        type(confirmPasswordTextboxLocator, data.getConfirmPassword());
        type(fullNameTextboxLocator, data.getFullName());
        type(phoneTextboxLocator, data.getPhone());
        type(dobTextboxLocator, data.getDob());

        click(getGenderLocator(data.getGender()));
        click(cityDropdownLocator);
        click(cityOptionLocator(data.getCity()));

        type(idNumberTextboxLocator, data.getIdNumber());
        type(emailTextboxLocator, data.getEmail());
        click(submitButton);

        waitClickable(confirmButton);
        click(confirmButton);

        waitClickable(closeSuccessPopupLocator);
        click(closeSuccessPopupLocator);
    }

    // public boolean isRegisterSuccessPopupDisplayed() {
    //     return isDisplayed(successPopup);
    // }
}
