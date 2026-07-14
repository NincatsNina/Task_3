package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов страницы
    private final By loginAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]");
    private final By makeBurgerHeader = By.xpath(".//h1[text()='Соберите бургер']");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Step("Кликнуть по кнопке 'Войти в аккаунт' на главной")
    public void clickLoginAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginAccountButton)).click();
    }

    @Step("Кликнуть по кнопке 'Личный Кабинет'")
    public void clickPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
    }

    @Step("Кликнуть по вкладке 'Булки'")
    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(activeTab, "Булки"));
    }

    @Step("Кликнуть по вкладке 'Соусы'")
    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(activeTab, "Булки")));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(activeTab, "Соусы"));
    }

    @Step("Кликнуть по вкладке 'Начинки'")
    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(activeTab, "Булки")));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(activeTab, "Начинки"));
    }

    @Step("Получить название активной вкладки")
    public String getActiveTabName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab)).getText();
    }

    @Step("Проверить, отображается ли заголовок 'Соберите бургер'")
    public boolean isMakeBurgerHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(makeBurgerHeader)).isDisplayed();
    }
}
