package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class AddEmployeePage extends BasePage {

    private final By firstName =
            By.cssSelector("input[name='firstName']");

    private final By lastName =
            By.cssSelector("input[name='lastName']");

    private final By saveButton =
            By.xpath("//button[normalize-space()='Save']");

    private final By requiredFirstName =
            By.xpath("//label[normalize-space()='First Name']/ancestor::div[contains(@class,'oxd-input-group')]//*[normalize-space()='Required']");

    private final By personalDetailsHeader =
            By.xpath("//h6[normalize-space()='Personal Details']");

    private final By formLoader =
            By.cssSelector("div.oxd-form-loader");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Add Employee fields are displayed")
    public boolean areRequiredFieldsDisplayed() {
        return isDisplayed(firstName) && isDisplayed(lastName);
    }

    @Step("Enter first name: {value}")
    public AddEmployeePage enterFirstName(String value) {
        type(firstName, value);
        return this;
    }

    @Step("Enter last name: {value}")
    public AddEmployeePage enterLastName(String value) {
        type(lastName, value);
        return this;
    }

    @Step("Save employee")
    public void save() {
        WaitUtils.invisibility(driver, formLoader);
        click(saveButton);
    }

    @Step("Verify First Name required validation")
    public boolean isFirstNameRequiredDisplayed() {
        return isDisplayed(requiredFirstName);
    }

    @Step("Verify Personal Details page is displayed")
    public boolean isPersonalDetailsDisplayed() {
        return isDisplayed(personalDetailsHeader);
    }
}