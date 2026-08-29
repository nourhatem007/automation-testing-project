package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.JsonDataReader;
import utils.RetryAnalyzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PIMTests extends BaseTest {

    private void loginAndOpenPIM() {
        LoginPage login = new LoginPage(DriverFactory.getDriver());
        login.login(JsonDataReader.get("login.validUsername"), JsonDataReader.get("login.validPassword"));
        new DashboardPage(DriverFactory.getDriver()).openPIM();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Search for a known employee from PIM Employee List")
    public void searchExistingEmployeeTest() {
        loginAndOpenPIM();
        PIMPage pim = new PIMPage(DriverFactory.getDriver());
        pim.openEmployeeList();
        String employeeName = JsonDataReader.get("employees.knownEmployee");

        pim.searchEmployee(employeeName);

        Assert.assertTrue(pim.resultContains(employeeName),
                "Employee search results should contain the known employee");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Search for an employee that does not exist")
    public void searchNonExistingEmployeeTest() {
        loginAndOpenPIM();
        PIMPage pim = new PIMPage(DriverFactory.getDriver());
        pim.openEmployeeList();
        pim.searchEmployee(JsonDataReader.get("employees.nonExistingEmployee"));

        Assert.assertTrue(pim.isNoRecordsFoundDisplayed(),
                "No Records Found should be displayed for a non-existing employee");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify PIM Add Employee page URL and required fields")
    public void openAddEmployeePageTest() {
        loginAndOpenPIM();
        AddEmployeePage addEmployee = new PIMPage(DriverFactory.getDriver()).openAddEmployee();

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("/pim/addEmployee"),
                "URL should contain /pim/addEmployee");
        Assert.assertTrue(addEmployee.areRequiredFieldsDisplayed(),
                "First Name and Last Name fields should be displayed");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify First Name is required when saving an employee with only Last Name")
    public void addEmployeeEmptyRequiredFieldTest() {
        loginAndOpenPIM();
        AddEmployeePage addEmployee = new PIMPage(DriverFactory.getDriver()).openAddEmployee();
        addEmployee.enterLastName(JsonDataReader.get("employees.lastNameOnly"));
        addEmployee.save();

        Assert.assertTrue(addEmployee.isFirstNameRequiredDisplayed(),
                "Required validation should be displayed under First Name");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("End-to-end test: create a new employee, verify Personal Details, then find the employee")
    public void addEmployeeEndToEndTest() {
        loginAndOpenPIM();
        AddEmployeePage addEmployee = new PIMPage(DriverFactory.getDriver()).openAddEmployee();

        String suffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmssSSS"));
        String firstName = JsonDataReader.get("newEmployee.firstNamePrefix") + suffix;
        String lastName = JsonDataReader.get("newEmployee.lastNamePrefix") + suffix;
        String fullName = firstName + " " + lastName;

        addEmployee.enterFirstName(firstName)
                .enterLastName(lastName)
                .save();

        Assert.assertTrue(addEmployee.isPersonalDetailsDisplayed(),
                "Personal Details page should open after saving a new employee");

        new DashboardPage(DriverFactory.getDriver()).openPIM();
        PIMPage pim = new PIMPage(DriverFactory.getDriver());
        pim.openEmployeeList();
        pim.searchEmployee(fullName);

        Assert.assertTrue(pim.resultContains(fullName),
                "Newly created employee should appear in Employee List search results");
    }
}
