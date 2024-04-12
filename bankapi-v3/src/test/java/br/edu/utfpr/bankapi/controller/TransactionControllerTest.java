package br.edu.utfpr.bankapi.controller;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.TransactionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    TestEntityManager entityManager;

    Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Teste da Silva Sauro", 12345, 0, 0);
        entityManager.persist(account);
    }

    @Test
    void shouldReturn400WhenTransferWithEmptyRequest() throws Exception {
        // Arrange
        String emptyJson = "{}";

        // Act
        var response = mvc.perform(MockMvcRequestBuilders.post("/transaction/deposit")
                .content(emptyJson).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        Assertions.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    @Test
    void shouldReturn400WhenTransferWithInvalidData() throws Exception {
        // Arrange
        long receiverAccountNumber = -1;
        double amount = -1;
        var dto = new DepositDTO(receiverAccountNumber, amount);
        String json = new ObjectMapper().writeValueAsString(dto);

        // Act
        var response = mvc.perform(MockMvcRequestBuilders.post("/transaction/deposit")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        Assertions.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    @Test
    void shouldReturn201WhenTransferWithValidRequest() throws Exception {
        // Arrange
        long receiverAccountNumber = 12345;
        long amount = 200;
        var dto = new DepositDTO(receiverAccountNumber, amount);
        String json = new ObjectMapper().writeValueAsString(dto);
        var account = new Account("Teste da Silva Sauro", receiverAccountNumber, 0, 0);
        entityManager.persist(account);

        // Act
        var response = mvc.perform(MockMvcRequestBuilders.post("/transaction/deposit")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        Assertions.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        var responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"balance\":200"));
        Assertions.assertTrue(responseBody.contains("\"number\":" + receiverAccountNumber));
    }

    @Test
    void shouldReturn201WithValidResponseWhenTransferWithValidRequest() throws Exception {
        // Arrange
        long receiverAccountNumber = 12345;
        long amount = 200;
        var dto = new DepositDTO(receiverAccountNumber, amount);
        String json = new ObjectMapper().writeValueAsString(dto);

        // Act + Assert
        var response = mvc.perform(MockMvcRequestBuilders.post("/transaction/deposit")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("balance",
                        Matchers.equalTo(amount)))
                .andExpect(MockMvcResultMatchers.jsonPath("number",
                        Matchers.equalTo(receiverAccountNumber)))
                .andExpect(MockMvcResultMatchers.jsonPath("type",
                        Matchers.equalTo(TransactionType.DEPOSIT)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
