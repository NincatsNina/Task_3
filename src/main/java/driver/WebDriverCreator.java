package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class WebDriverCreator {

    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser");
        WebDriver driver;

        if (browser != null && browser.equalsIgnoreCase("yandex")) {
            driver = createYandexDriver();
        } else {
            driver = createChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        String yandexBinary = System.getenv("YANDEX_BROWSER_PATH");
        if (yandexBinary != null && !yandexBinary.isEmpty()) {
            options.setBinary(yandexBinary);
        }

        return new ChromeDriver(options);
    }
}
