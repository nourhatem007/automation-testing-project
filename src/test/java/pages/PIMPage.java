package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class PIMPage extends BasePage {

    private final By employeeListLink =
            By.xpath("//a[normalize-space()='Employee List']");

    private final By addEmployeeLink =
            By.xpath("//a[normalize-space()='Add Employee']");

    private final By employeeNameInput =
            By.xpath("//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private final By searchButton =
            By.xpath("//button[normalize-space()='Search']");

    private final By resetButton =
            By.xpath("//button[normalize-space()='Reset']");

    private final By noRecords =
            By.xpath("//*[normalize-space()='No Records Found']");

    private final By resultRows =
            By.cssSelector("div.oxd-table-body div.oxd-table-row");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Employee List")
    public void openEmployeeList() {
        click(employeeListLink);
    }

    @Step("Open Add Employee")
    public AddEmployeePage openAddEmployee() {
        click(addEmployeeLink);
        return new AddEmployeePage(driver);
    }

    @Step("Search employee by name: {name}")
    public void searchEmployee(String name) {
        type(employeeNameInput, name);
        click(searchButton);
    }

    @Step("Check that search results contain employee: {name}")
    public boolean resultContains(String name) {

        By employeeRow = By.xpath(
                "//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-row')]" +
                        "[contains(normalize-space(.),\"" + name + "\")]"
        );

        return WaitUtils.visible(driver, employeeRow) != null;
    }

    @Step("Verify No Records Found message")
    public boolean isNoRecordsFoundDisplayed() {
        return isDisplayed(noRecords);
    }

    @Step("Verify Employee List page is displayed")
    public boolean isEmployeeListDisplayed() {
        return isDisplayed(employeeNameInput);
    }
}