package pages.admin;

import base.BasePage;
import components.AdminSidebarComponent;
import org.openqa.selenium.WebDriver;

public class AdminDashboardPage extends BasePage {
    private final AdminSidebarComponent sidebar;

    public AdminDashboardPage(WebDriver driver) {
        super(driver);
        this.sidebar = new AdminSidebarComponent(driver);
    }

    public AdminSidebarComponent sidebar() {
        return sidebar;
    }
}
