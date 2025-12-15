package page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NovoLeilaoPage extends BasePage {

    // --- SELETORES CORRIGIDOS ---
    private By campoNome         = By.id("nome");
    // AQUI ESTAVA O ERRO: O ID correto é 'valorInicial', não 'valor'
    private By campoValor        = By.id("valorInicial");
    private By campoDataAbertura = By.id("dataAbertura");
    private By botaoSalvar       = By.id("button-submit");

    // Seletor genérico para mensagens de erro
    private By mensagemErro      = By.cssSelector(".alert-danger");

    public NovoLeilaoPage(WebDriver driver) {
        super(driver);
    }

    public void preencherFormulario(String nome, String valor, String data) {
        // 1. Wait Explícito: Espera o campo Nome estar visível antes de fazer qualquer coisa
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoNome));

        // 2. Preenchimento seguro
        driver.findElement(campoNome).sendKeys(nome);
        driver.findElement(campoValor).sendKeys(valor); // Agora buscará pelo ID 'valorInicial'

        // 3. Lógica da data (limpar garante que não fica lixo no campo)
        WebElement elementoData = driver.findElement(campoDataAbertura);
        elementoData.clear();
        elementoData.sendKeys(data);
    }

    public LeiloesPage submeterFormulario() {
        click(botaoSalvar);
        // Retorna a página de listagem
        return new LeiloesPage(driver);
    }

    public void submeterFormularioComErro() {
        click(botaoSalvar);
        // Permanece na mesma página
    }

    public boolean isMensagemValidacaoVisivel() {
        return elementExists(mensagemErro);
    }

    public boolean isMensagemErroNomeVisivel() {
        return driver.getPageSource().contains("não deve estar em branco")
                || driver.getPageSource().contains("minimo 3 caracteres");
    }

    public boolean isMensagemErroValorVisivel() {
        return driver.getPageSource().contains("deve ser um valor maior de 0.1");
    }

    public boolean isMensagemErroDataVisivel() {
        return driver.getPageSource().contains("deve ser uma data no formato dd/MM/yyyy");
    }
}