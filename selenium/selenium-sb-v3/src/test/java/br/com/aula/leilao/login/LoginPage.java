package br.com.aula.leilao.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.aula.leilao.leiloes.LeiloesPage;

public class LoginPage {
    public static final String URL_LOGIN = "http://localhost:8080/login";
    public static final String URL_LEILOES = "http://localhost:8080/leiloes";

    private WebDriver browser;

    public LoginPage() {
        System.setProperty(
                "webdriver.chrome.driver",
                "drivers/chromedriver.exe");

        browser = new ChromeDriver();
        browser.navigate().to(URL_LOGIN);
    }

    public void fechar() {
        browser.quit();
    }

    public void preencherFormularioDeLogin(String username, String password) {
        browser.findElement(By.id("username")).sendKeys(username);
        browser.findElement(By.id("password")).sendKeys(password);
    }

    public LeiloesPage submeterFormularioDeLogin() {
        browser.findElement(By.id("button-submit")).click();
        return new LeiloesPage(browser);
    }

    public boolean isLoginPage() {
        return browser.getCurrentUrl().equals(URL_LOGIN);
    }

    public boolean isLeilaoPage() {
        return browser.getCurrentUrl().equals(URL_LEILOES);
    }

    public String getNomeUsuarioLogado() {
        return browser.findElement(By.id("usuario-logado")).getText();
    }

    public boolean isLoginPageComErro() {
        return browser.getCurrentUrl().equals(URL_LOGIN + "?error");
    }

    public boolean contemTexto(String texto) {
        return browser.getPageSource().contains(texto);
    }
}
