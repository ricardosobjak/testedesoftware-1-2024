package br.com.aula.leilao;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    WebDriver browser;

    @BeforeEach
    void beforeEach() {
        System.setProperty(
                "webdriver.chrome.driver",
                "drivers/chromedriver.exe");

        browser = new ChromeDriver();

        browser.navigate().to("http://localhost:8080/login");
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    @Test
    void deveriaFazerLoginComSucesso() {
        browser.findElement(By.id("username")).sendKeys("fulano");
        browser.findElement(By.id("password")).sendKeys("pass");
        browser.findElement(By.id("button-submit")).click();

        // Asserts
        // Verificar se a URL mudou para .../leiloes
        Assert.assertTrue(
                browser.getCurrentUrl().equals("http://localhost:8080/leiloes"));

        // Verificar se o nome do usuário logado é apresentado na Div
        Assert.assertTrue(
                browser.findElement(
                        By.id("usuario-logado")).getText().equals("fulano"));
    }

    @Test
    void naoDeveriaFazerLoginComSucesso() {
        browser.findElement(By.id("username")).sendKeys("x");
        browser.findElement(By.id("password")).sendKeys("y");
        browser.findElement(By.id("button-submit")).click();

        // Asserts
        // Verificar se a URL está em .../login
        Assert.assertTrue(
                browser.getCurrentUrl().equals("http://localhost:8080/login?error"));

        // Verificar se a URL não é a .../leiloes
        Assert.assertFalse(
                browser.getCurrentUrl().equals("http://localhost:8080/leiloes"));

        // Verificar se contém a mensagem de erro na página
        Assert.assertTrue(browser.getPageSource().contains("Usuário e senha inválidos"));
    }

}
