package br.com.aula.leilao.leiloes;

import org.openqa.selenium.WebDriver;

public class LeiloesPage {
    public static final String URL_LEILOES = "http://localhost:8080/leiloes";
    
    private WebDriver browser;

    public LeiloesPage(WebDriver browser) {
        this.browser = browser;
    }

    public void fechar() {
        browser.quit();
    }

    public CadastroLeilaoPage abrirPaginaDeCadastro() {
        browser.navigate().to(CadastroLeilaoPage.URL_CADASTRO);
        return new CadastroLeilaoPage(browser);
    }
}
