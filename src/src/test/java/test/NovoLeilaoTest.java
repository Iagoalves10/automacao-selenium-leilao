package test;

import Config.TestConfig;
import page.LeiloesPage;
import page.LoginPage;
import page.NovoLeilaoPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // Importante
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@DisplayName("Testes de Cadastro de Novo Leilão")
public class NovoLeilaoTest {

    private WebDriver driver;
    private LeiloesPage leiloesPage;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void inicializar() {
        // --- INÍCIO DA CONFIGURAÇÃO ANTI-POPUP ---
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-infobars");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        // --- FIM DA CONFIGURAÇÃO ---

        driver = new ChromeDriver(options);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navegarParaPaginaDeLogin();
        loginPage.preencherFormularioDeLogin(TestConfig.USUARIO, TestConfig.SENHA);
        loginPage.submeterLogin();

        leiloesPage = new LeiloesPage(driver);
    }

    @AfterEach
    public void finalizar() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Deve validar campos obrigatórios no cadastro de leilão")
    public void deveValidarCamposObrigatorios() {
        NovoLeilaoPage novoLeilaoPage = leiloesPage.clicarNovoLeilao();

        novoLeilaoPage.preencherFormulario("", "", "");
        novoLeilaoPage.submeterFormularioComErro();

        Assertions.assertAll("Validação de campos obrigatórios",
                () -> Assertions.assertTrue(novoLeilaoPage.isMensagemErroNomeVisivel(), "Erro no Nome"),
                () -> Assertions.assertTrue(novoLeilaoPage.isMensagemErroValorVisivel(), "Erro no Valor"),
                () -> Assertions.assertTrue(novoLeilaoPage.isMensagemErroDataVisivel(), "Erro na Data")
        );
    }

    @Test
    @DisplayName("Deve cadastrar um leilão com sucesso")
    public void deveCadastrarLeilaoComSucesso() {
        NovoLeilaoPage novoLeilaoPage = leiloesPage.clicarNovoLeilao();

        String nome = "Leilão " + System.currentTimeMillis();
        String valor = "500.00";
        String hoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        novoLeilaoPage.preencherFormulario(nome, valor, hoje);

        leiloesPage = novoLeilaoPage.submeterFormulario();

        Assertions.assertTrue(leiloesPage.isLeilaoCadastrado(nome, valor, hoje));
    }
}