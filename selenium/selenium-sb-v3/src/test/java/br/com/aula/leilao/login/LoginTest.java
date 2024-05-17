package br.com.aula.leilao.login;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {
    LoginPage loginPage;

    @BeforeEach
    void beforeEach() {
        loginPage = new LoginPage();
    }

    @AfterEach
    void afterEach() {
        loginPage.fechar();
    }

    @Test
    void deveriaFazerLoginComSucesso() {
        loginPage.preencherFormularioDeLogin("fulano", "pass");
        loginPage.submeterFormularioDeLogin();

        // Asserts
        // Verificar se a URL mudou para .../leiloes
        Assert.assertTrue(loginPage.isLeilaoPage());

        // Verificar se a URL não é .../login
        Assert.assertFalse(loginPage.isLoginPage());

        // Verificar se o nome do usuário logado está apresentado na Div
        Assert.assertEquals("fulano", loginPage.getNomeUsuarioLogado());
    }

    @Test
    void naoDeveriaFazerLoginComSucesso() {
        loginPage.preencherFormularioDeLogin("x", "y");
        loginPage.submeterFormularioDeLogin();

        // Asserts
        // Verificar se a URL está em .../login
        Assert.assertTrue(loginPage.isLoginPageComErro());

        // Verificar se a URL não é a .../leiloes
        Assert.assertFalse(loginPage.isLeilaoPage());

        // Verificar se contém a mensagem de erro na página
        Assert.assertTrue(loginPage.contemTexto("Usuário e senha inválidos"));
    }
}
