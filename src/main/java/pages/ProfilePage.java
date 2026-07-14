package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By constructorLink = By.xpath(".//p[text()='Конструктор']");
    private final By logoLink = By.xpath(".//*[contains(@class, 'AppHeader_header__logo')]/a");
    private final By profileInfoText = By.xpath(".//p[contains(text(), 'В этом разделе вы можете изменить свои персональные данные')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Step("Ожидание загрузки страницы личного кабинета")
    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileInfoText));
    }

    @Step("Кликнуть по кнопке 'Выйти' в личном кабинете")
    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    @Step("Кликнуть по ссылке 'Конструктор' в шапке")
    public void clickConstructorLink() {
        driver.findElement(constructorLink).click();
    }

    @Step("Кликнуть на логотип Stellar Burgers")
    public void clickLogoLink() {
        wait.until(ExpectedConditions.elementToBeClickable(logoLink)).click();
    }

    @Step("Проверить, отображается ли инфо-текст профиля")
    public boolean isProfileInfoDisplayed() {
        return driver.findElement(profileInfoText).isDisplayed();
    }
}
