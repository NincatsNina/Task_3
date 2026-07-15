import clients.UserApiClient;
import driver.WebDriverCreator;
import models.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;
import pages.LoginPage;
import pages.ProfilePage;

@DisplayName("Тесты навигации по сайту")
public class NavigationTest {
    private WebDriver driver;
    private UserApiClient userApiClient;
    private String token;
    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        userApiClient = new UserApiClient();
        User user = new User()
                .setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password(8, 12))
                .setName(faker.name().firstName());
        token = userApiClient.createUser(user).path("accessToken");

        driver = WebDriverCreator.createWebDriver();
        driver.get("https://qa-stellarburgers.education-services.ru/login");
        new LoginPage(driver).login(user.getEmail(), user.getPassword());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
        if (token != null) userApiClient.deleteUser(token);
    }

    @Test
    @DisplayName("Переход в Личный кабинет из Конструктора")
    public void navigateToProfilePageTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        constructorPage.clickPersonalAccountButton();
        profilePage.waitForLoad();
        Assertions.assertTrue(profilePage.isProfileInfoDisplayed());
    }

    @Test
    @DisplayName("Переход из Личного кабинета в Конструктор по ссылке")
    public void navigateFromProfileToConstructorViaLinkTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        constructorPage.clickPersonalAccountButton();
        profilePage.waitForLoad();
        profilePage.clickConstructorLink();
        Assertions.assertTrue(constructorPage.isMakeBurgerHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход из Личного кабинета в Конструктор по Логотипу")
    public void navigateFromProfileToConstructorViaLogoTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        constructorPage.clickPersonalAccountButton();
        profilePage.waitForLoad();
        profilePage.clickLogoLink();
        Assertions.assertTrue(constructorPage.isMakeBurgerHeaderDisplayed());
    }
}
