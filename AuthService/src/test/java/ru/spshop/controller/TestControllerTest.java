package ru.spshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.spshop.dto.UserDTO;
import ru.spshop.repositories.UserRepository;
import ru.spshop.service.RegisterService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterService registerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String email = "john.doe@example.com";
    private final String password = "password";
    private final String baseEndpoint = "/tests";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserDTO request = new UserDTO(email, password);
        registerService.registerUser(request);
    }

    @Test
    @WithMockUser
    void getTest_shouldReturnAlive() throws Exception {
        mockMvc.perform(get(baseEndpoint + "/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am alive"));
    }

    @Test
    void getTest_shouldReturnException() throws Exception {
        mockMvc.perform(get(baseEndpoint + "/test"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getAllUsers_shouldReturnList_whenAdmin() throws Exception {
        mockMvc.perform(get(baseEndpoint + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(email));
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get(baseEndpoint + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getAllUsersWithUser() throws Exception {
        mockMvc.perform(get(baseEndpoint + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsersWithAdmin() throws Exception {
        mockMvc.perform(get(baseEndpoint + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
