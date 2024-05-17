package br.com.aula.leilao.leiloes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.aula.leilao.login.LoginPage;

public class CadastroLeilaoTest {
    CadastroLeilaoPage cadastroLeilaoPage;
    LeiloesPage leiloesPage;

    @BeforeEach
    void beforeEach() {
        LoginPage loginPage = new LoginPage();
        loginPage.preencherFormularioDeLogin("fulano", "pass");
        leiloesPage = loginPage.submeterFormularioDeLogin();

        cadastroLeilaoPage = leiloesPage.abrirPaginaDeCadastro();
    }

    @AfterEach
    void afterEach() {
        cadastroLeilaoPage.fechar();
    }

    @Test
    void deveriaCadastarComSucesso() {
        String hoje = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String nome = "Leilão do dia " + hoje;
        String valor = "400.00";

        cadastroLeilaoPage.preencherFormulario(nome, valor, hoje);
        cadastroLeilaoPage.submeterFormulario();
    }

    @Test
    void naoDeveriaCadastrarComCamposInvalidos() {

    }

}
