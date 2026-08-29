package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class DashboardPage extends BasePage {
    private final By dashboardHeader = By.xpath("//h6[normalize-space()='Dashboard']");
    private final By pimMenu = By.xpath("//span[normalize-space()='PIM']");
    private final By adminMenu = By.xpath("//span[normalize-space()='Admin']");
    private final By footer = By.xpath("//*[contains(normalize-space(),'OrangeHRM, Inc')]");
    private final By footerLink = By.xpath("//a[contains(normalize-space(),'OrangeHRM, Inc')]");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Dashboard header is displayed")
    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardHeader);
    }

    @Step("Open PIM menu")
    public void openPIM() {
        click(pimMenu);
    }

    @Step("Open Admin menu")
    public void openAdmin() {
        click(adminMenu);
    }

    @Step("Verify footer contains OrangeHRM, Inc")
    public boolean isFooterBrandDisplayed() {
        return isDisplayed(footer);
    }

    @Step("Click OrangeHRM, Inc footer link")
    public void clickFooterBrand() {
        click(footerLink);
    }

    @Step("Check required sidebar menu items")
    public boolean hasSidebarItems(List<String> menuItems) {
        for (String item : menuItems) {
            By locator = By.xpath("//span[normalize-space()='" + item + "'] | //a[normalize-space()='" + item + "']");
            if (!isDisplayed(locator)) {
                return false;
            }
        }
        return true;
    }
}
