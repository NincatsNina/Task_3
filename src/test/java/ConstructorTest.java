import driver.WebDriverCreator;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;

@DisplayName("Тесты разделов Конструктора")
public class ConstructorTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        driver.get("https://qa-stellarburgers.education-services.ru");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void navigateToSaucesTabTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickSaucesTab();
        Assertions.assertEquals("Соусы", constructorPage.getActiveTabName());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void navigateToFillingsTabTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickFillingsTab();
        Assertions.assertEquals("Начинки", constructorPage.getActiveTabName());
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    public void navigateToBunsTabTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickSaucesTab();
        constructorPage.clickBunsTab();
        Assertions.assertEquals("Булки", constructorPage.getActiveTabName());
    }
}
