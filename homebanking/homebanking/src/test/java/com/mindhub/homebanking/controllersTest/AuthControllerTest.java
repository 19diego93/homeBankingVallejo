package com.mindhub.homebanking.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.servicesSecurity.JwtUtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Test class for AuthController
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc for testing

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper for JSON conversion

    @MockBean
    private PasswordEncoder passwordEncoder; // Mocked PasswordEncoder

    @MockBean
    private ClientService clientService; // Mocked ClientService

    @MockBean
    private AuthenticationManager authenticationManager; // Mocked AuthenticationManager

    @MockBean
    private UserDetailsService userDetailsService; // Mocked UserDetailsService

    @MockBean
    private JwtUtilService jwtUtilService; // Mocked JwtUtilService

    @MockBean
    private AccountService accountService; // Mocked AccountService

    private RegisterDTO registerDTO; // DTO for registration
    private LoginDTO loginDTO; // DTO for login

    @BeforeEach
    void setUp() {
        // Initialize registerDTO and loginDTO
        registerDTO = new RegisterDTO("John", "Doe", "john.doe@example.com", "Password@123");
        loginDTO = new LoginDTO("john.doe@example.com", "Password@123");
    }

    @Test
        // Test for user registration
    void testRegister() throws Exception {
        // Mock behavior for registration
        Mockito.when(clientService.existsClientByEmail(registerDTO.email())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(registerDTO.password())).thenReturn("encodedPassword");

        // Perform registration API call
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated()); // Expect successful creation
    }

    @Test
        // Test for user login
    void testLogin() throws Exception {
        // Mock behavior for login
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);
        Mockito.when(userDetailsService.loadUserByUsername(loginDTO.email())).thenReturn(Mockito.mock(UserDetails.class));
        Mockito.when(jwtUtilService.generateToken(Mockito.any())).thenReturn("mockJwtToken");

        // Perform login API call
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk()); // Expect successful login
    }
}

