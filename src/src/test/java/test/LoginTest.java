package test;

import Config.TestConfig;
import page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@DisplayName("Testes de Login")
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void inicializar() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void finalizar() {
        driver.quit();
    }

    @Test
    @DisplayName("Deve realizar login válido e ir para a página de leilões")
    public void deveRealizarLoginValido() {
        loginPage.navegarParaPaginaDeLogin();
        loginPage.preencherFormularioDeLogin(TestConfig.USUARIO, TestConfig.SENHA);
        loginPage.submeterLogin();

        Assertions.assertTrue(loginPage.isPaginaDeLeiloes());

    }


    @Test
    @DisplayName("Não deve realizar login com dados inválidos")
    public void naoDeveRealizarLoginComDadosInvalidos() {
        loginPage.navegarParaPaginaDeLogin();
        loginPage.preencherFormularioDeLogin("invalido", "errado");
        loginPage.submeterLogin();

        Assertions.assertTrue(loginPage.isPaginaDeLogin());
        Assertions.assertTrue(loginPage.isMensagemDeErroVisivel());
    }

    @Test
    @DisplayName("Não deve acessar página restrita sem login")
    public void naoDeveAcessarPaginaRestritaSemLogin() {
        driver.navigate().to(TestConfig.BASE_URL + "/leiloes/2");

        Assertions.assertTrue(loginPage.isPaginaDeLogin());
        Assertions.assertFalse(driver.getPageSource().contains("Dados do Leilão"));
    }
}
