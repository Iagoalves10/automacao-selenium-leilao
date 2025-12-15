package page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LeiloesPage extends BasePage {

    private By botaoNovoLeilao = By.id("novo_leilao_link");

    public LeiloesPage(WebDriver driver) {
        super(driver);
    }

    public NovoLeilaoPage clicarNovoLeilao() {
        click(botaoNovoLeilao);
        return new NovoLeilaoPage(driver);
    }

    public boolean isLeilaoCadastrado(String nome, String valor, String data) {
        // 1. Instancia o Wait para aguardar o carregamento
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Define o localizador da linha.
        // DICA: Usar "contains(., 'texto')" é melhor que "contains(text(), 'texto')"
        By locatorLinha = By.xpath("//tr[td[contains(.,'" + nome + "')]]");

        try {
            // 3. Aguarda até que a linha específica esteja visível na tabela
            WebElement linhaDaTabela = wait.until(ExpectedConditions.visibilityOfElementLocated(locatorLinha));

            // 4. Valida o restante dos dados
            String textoDaLinha = linhaDaTabela.getText();
            return textoDaLinha.contains(data) && textoDaLinha.contains(valor);

        } catch (Exception e) {
            // Se der timeout, significa que o leilão não apareceu
            return false;
        }
    }
}