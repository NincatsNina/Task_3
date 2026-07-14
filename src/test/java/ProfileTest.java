import clients.UserApiClient;
import driver.WebDriverCreator;
import models.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;
import pages.LoginPage;
import pages.ProfilePage;

@DisplayName("Тесты личного кабинета")
public class ProfileTest {
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
    @DisplayName("Выход из аккаунта в Личном кабинете")
    public void logoutFromProfilePageTest() {
        ConstructorPage constructorPage = new ConstructorPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        constructorPage.clickPersonalAccountButton();
        profilePage.waitForLoad();
        profilePage.clickLogoutButton();
        Assertions.assertTrue(loginPage.isLoginButtonDisplayed());
    }
}