import clients.UserApiClient;
import driver.WebDriverCreator;
import models.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.RegisterPage;

@DisplayName("Тесты всех точек входа")
public class LoginTest {
        private WebDriver driver;
        private UserApiClient userApiClient;
        private User user;
        private String token;
        private final Faker faker = new Faker();

        @BeforeEach
        public void setUp() {
            userApiClient = new UserApiClient();
            user = new User()
                    .setEmail(faker.internet().emailAddress())
                    .setPassword(faker.internet().password(8, 12))
                    .setName(faker.name().firstName());
            token = userApiClient.createUser(user).path("accessToken");
            driver = WebDriverCreator.createWebDriver();
        }

        @AfterEach
        public void tearDown() {
            if (driver != null) driver.quit();
            if (token != null) userApiClient.deleteUser(token);
        }

        @Test
        @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
        public void loginViaMainPageButtonTest() {
            driver.get("https://qa-stellarburgers.education-services.ru");
            ConstructorPage constructorPage = new ConstructorPage(driver);
            LoginPage loginPage = new LoginPage(driver);

            constructorPage.clickLoginAccountButton();
            loginPage.login(user.getEmail(), user.getPassword());
            Assertions.assertTrue(constructorPage.isMakeBurgerHeaderDisplayed());
        }

        @Test
        @DisplayName("Вход через кнопку 'Личный кабинет' в шапке")
        public void loginViaPersonalAccountHeaderLinkTest() {
            driver.get("https://qa-stellarburgers.education-services.ru");
            ConstructorPage constructorPage = new ConstructorPage(driver);
            LoginPage loginPage = new LoginPage(driver);

            constructorPage.clickPersonalAccountButton();
            loginPage.login(user.getEmail(), user.getPassword());
            Assertions.assertTrue(constructorPage.isMakeBurgerHeaderDisplayed());
        }

        @Test
        @DisplayName("Вход через кнопку в форме регистрации")
        public void loginViaRegistrationFormLinkTest() {
            driver.get("https://qa-stellarburgers.education-services.ru/register");
            RegisterPage registerPage = new RegisterPage(driver);
            LoginPage loginPage = new LoginPage(driver);
            ConstructorPage constructorPage = new ConstructorPage(driver);

            registerPage.clickLoginLink();
            loginPage.login(user.getEmail(), user.getPassword());
            Assertions.assertTrue(constructorPage.isMakeBurgerHeaderDisplayed());
        }

        @Test
        @DisplayName("Вход через кнопку в форме восстановления пароля")
        public void loginViaForgotPasswordFormLinkTest() {
            driver.get("https://qa-stellarburgers.education-services.ru/forgot-password");
            ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
            LoginPage loginPage = new LoginPage(driver);
            ConstructorPage constructorPage = new ConstructorPage(driver);

            forgotPasswordPage.clickLoginLink();
            loginPage.login(user.getEmail(), user.getPassword());
            Assertions.assertTrue(constructorPage.isMakeBurgerHeaderDisplayed());
        }
}
