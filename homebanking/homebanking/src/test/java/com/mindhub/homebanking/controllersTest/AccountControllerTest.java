package com.mindhub.homebanking.controllersTest;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ClientService clientService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        when(accountService.getListAccountsDto(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There are no Accounts"));

        verify(accountService, times(1)).getListAccountsDto(any());
    }

    @Test
    public void testGetAccountById() throws Exception {
        Account account = new Account("12345678", 0); // Crea la cuenta sin establecer el ID
        when(accountService.getAccountById(1L)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("12345678"));

        verify(accountService, times(1)).getAccountById(1L);
    }

    @Test
    @WithMockUser(username = "user@example.com")
    public void testCreateAccount() throws Exception {
        Client client = new Client();
        client.setEmail("user@example.com");
        when(clientService.getClientByEmail("user@example.com")).thenReturn(client);

        mockMvc.perform(post("/api/clients/accounts/current"))
                .andExpect(status().isCreated());

        verify(clientService, times(1)).getClientByEmail("user@example.com");
    }

    @Test
    @WithMockUser(username = "user@example.com")
    public void testGetClientAccounts() throws Exception {
        Client client = new Client();
        client.setEmail("user@example.com");
        Account account = new Account("12345678", 0);
        client.addAccount(account);

        when(clientService.getClientByEmail("user@example.com")).thenReturn(client);

        mockMvc.perform(get("/api/clients/accounts/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("12345678"));

        verify(clientService, times(1)).getClientByEmail("user@example.com");
    }
}
