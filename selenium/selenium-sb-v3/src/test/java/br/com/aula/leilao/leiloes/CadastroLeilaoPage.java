package br.com.aula.leilao.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CadastroLeilaoPage {
    public static final String URL_CADASTRO = "http://localhost:8080/leiloes/new";
    

    private WebDriver browser;
    
    public CadastroLeilaoPage(WebDriver browser) {
        this.browser = browser;
    }

    public void fechar() {
        browser.quit();
    }

    public void preencherFormulario(String nome, String valorInicial, String dataAbertura) {
        browser.findElement(By.id("nome")).sendKeys(nome);
        browser.findElement(By.id("valorInicial")).sendKeys(valorInicial);
        browser.findElement(By.id("dataAbertura")).sendKeys(dataAbertura);
    }

    public void submeterFormulario() {
        browser.findElement(By.id("button-submit")).click();
    }
}
