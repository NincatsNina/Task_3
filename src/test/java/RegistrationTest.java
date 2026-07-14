import clients.UserApiClient;
import driver.WebDriverCreator;
import io.qameta.allure.Description;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.RegisterPage;

@DisplayName("Тесты регистрации")
public class RegistrationTest {
    private WebDriver driver;
    private final Faker faker = new Faker();
    private String emailToDelete;
    private String passwordToDelete;

    @BeforeEach
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        driver.get("https://qa-stellarburgers.education-services.ru/register");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        if (emailToDelete != null) {
            UserApiClient apiClient = new UserApiClient();
            models.UserCreds creds = new models.UserCreds().setEmail(emailToDelete).setPassword(passwordToDelete);
            String token = apiClient.loginUser(creds).path("accessToken");
            if (token != null) {
                apiClient.deleteUser(token);
            }
        }
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверяем, что при заполнении всех полей валидными данными происходит переход на страницу логина")
    public void successfulRegistrationTest() {
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        emailToDelete = email;
        passwordToDelete = password;

        registerPage.fillRegistrationForm(name, email, password);
        registerPage.clickRegisterButton();

        Assertions.assertTrue(loginPage.isLoginButtonDisplayed(), "Переход на страницу входа не произошел");
    }

    @Test
    @DisplayName("Ошибка регистрации при некорректном пароле (меньше 6 символов)")
    @Description("Проверяем отображение текста ошибки под полем пароля")
    public void passwordTooShortRegistrationErrorTest() {
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.fillRegistrationForm(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password(3, 5));
        registerPage.clickRegisterButton();

        Assertions.assertTrue(registerPage.isPasswordErrorDisplayed(), "Сообщение о некорректном пароле не отобразилось");
    }
}
