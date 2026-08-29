package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.DriverFactory;
import utils.JsonDataReader;
import utils.RetryAnalyzer;

import java.util.List;
import java.util.Set;

public class AdminAndUITests extends BaseTest {

    private void login() {
        new LoginPage(DriverFactory.getDriver()).login(
                JsonDataReader.get("login.validUsername"),
                JsonDataReader.get("login.validPassword")
        );
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Admin > User Management > Users > Add form contains required fields")
    public void verifyAddUserPageTest() {
        login();
        DashboardPage dashboard = new DashboardPage(DriverFactory.getDriver());
        dashboard.openAdmin();

        AdminPage admin = new AdminPage(DriverFactory.getDriver());
        admin.openUserManagement();
        admin.openUsers();
        admin.clickAdd();

        Assert.assertTrue(admin.areRequiredFieldsDisplayed(),
                "Add User form should contain User Role, Employee Name, Username and Password fields");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.MINOR)
    @Description("Verify footer branding link opens OrangeHRM website")
    public void verifyFooterBrandingLinkTest() {
        login();
        DashboardPage dashboard = new DashboardPage(DriverFactory.getDriver());

        Assert.assertTrue(dashboard.isFooterBrandDisplayed(),
                "Footer should contain OrangeHRM, Inc");

        WebDriver driver = DriverFactory.getDriver();
        String originalWindow = driver.getWindowHandle();
        int windowsBefore = driver.getWindowHandles().size();
        dashboard.clickFooterBrand();

        if (driver.getWindowHandles().size() > windowsBefore) {
            Set<String> windows = driver.getWindowHandles();
            for (String window : windows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
        }

        Assert.assertTrue(driver.getCurrentUrl().contains("orangehrm.com"),
                "Footer link should open a URL containing orangehrm.com");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify all required sidebar menu items are displayed")
    public void verifySidebarMenuTest() {
        login();
        DashboardPage dashboard = new DashboardPage(DriverFactory.getDriver());

        List<String> expectedItems = List.of(
                "Admin", "PIM", "Leave", "Time", "Recruitment",
                "My Info", "Performance", "Dashboard", "Directory"
        );

        Assert.assertTrue(dashboard.hasSidebarItems(expectedItems),
                "Sidebar should contain Admin, PIM, Leave, Time, Recruitment, My Info, Performance, Dashboard and Directory");
    }
}
