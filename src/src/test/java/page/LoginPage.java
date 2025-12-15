package page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
// Imports adicionados para a Espera Explícita
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    // Ajuste a URL se necessário (ex: localhost:8080/login)
    private static final String URL_LOGIN = "http://localhost:8080/login";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navegarParaPaginaDeLogin() {
        driver.navigate().to(URL_LOGIN);
    }

    public void preencherFormularioDeLogin(String username, String password) {
        // Mantido By.name pois resolveu o Timeout anterior
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
    }

    public void submeterLogin() {
        // Tenta pelo ID do form, se falhar tenta clicar no botão de submit
        try {
            driver.findElement(By.id("login-form")).submit();
        } catch (NoSuchElementException e) {
            driver.findElement(By.cssSelector("button[type='submit']")).click();
        }
    }

    public boolean isPaginaDeLogin() {
        return driver.getCurrentUrl().contains("/login");
    }

    // --- CORREÇÃO AQUI ---
    public String getNomeUsuarioLogado() {
        try {
            // Cria um "esperador" de até 10 segundos
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Diz para o Selenium: "Espere até o elemento 'usuario-logado' estar VISÍVEL na tela"
            // Só depois disso ele executa o .getText()
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usuario-logado"))).getText();
        } catch (Exception e) {
            // Se passar 10 segundos e não aparecer, retorna null (e o teste falhará, mas sabemos o motivo)
            return null;
        }
    }

    public boolean isPaginaDeLeiloes() {
        return driver.getCurrentUrl().contains("/leiloes");
    }

    public boolean isMensagemDeErroVisivel() {
        return driver.getPageSource().contains("Usuário e senha inválidos");
    }


}