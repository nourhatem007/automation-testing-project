package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {
    private final By userManagement = By.xpath("//span[normalize-space()='User Management']");
    private final By usersLink = By.xpath("//a[normalize-space()='Users']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleLabel = By.xpath("//label[normalize-space()='User Role']");
    private final By employeeNameLabel = By.xpath("//label[normalize-space()='Employee Name']");
    private final By usernameLabel = By.xpath("//label[normalize-space()='Username']");
    private final By passwordLabel = By.xpath("//label[normalize-space()='Password']");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open User Management menu")
    public void openUserManagement() {
        click(userManagement);
    }

    @Step("Open Users page")
    public void openUsers() {
        click(usersLink);
    }

    @Step("Click Add User")
    public void clickAdd() {
        click(addButton);
    }

    @Step("Verify Add User form fields")
    public boolean areRequiredFieldsDisplayed() {
        return isDisplayed(userRoleLabel)
                && isDisplayed(employeeNameLabel)
                && isDisplayed(usernameLabel)
                && isDisplayed(passwordLabel);
    }
}
