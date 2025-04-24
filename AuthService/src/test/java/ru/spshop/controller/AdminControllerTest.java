package ru.spshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.spshop.dto.UserBlockDto;
import ru.spshop.dto.UserDTO;
import ru.spshop.model.User;
import ru.spshop.repositories.UserRepository;
import ru.spshop.service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    private final HttpServletResponse servletResponse = mock(HttpServletResponse.class);

    private final String email = "blocktest@example.com";
    private final String password = "password";
    private final String baseEndpoint = "/admin";

    private final UserBlockDto userBlockDto = new UserBlockDto(email);

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserDTO request = new UserDTO(email, password);
        registerService.registerUser(request, servletResponse);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldBlockUser() throws Exception {
        mockMvc.perform(post(baseEndpoint + "/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBlockDto)))
                .andExpect(status().isOk());

        User user = userRepository.findByEmail(email).orElseThrow();
        assertTrue(user.getLocked(), "User should be locked");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUnblockUser() throws Exception {
        // Сначала заблокируем
        mockMvc.perform(post(baseEndpoint + "/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBlockDto)))
                .andExpect(status().isOk());

        // Затем разблокируем
        mockMvc.perform(post(baseEndpoint + "/unblock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userBlockDto)))
                .andExpect(status().isOk());

        User user = userRepository.findByEmail(email).orElseThrow();
        assertFalse(user.getLocked(), "User should be unlocked");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenEmailNotFound() throws Exception {
        UserBlockDto userWrongEmailDto = new UserBlockDto("notfound@example.com");
        mockMvc.perform(post(baseEndpoint + "/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userWrongEmailDto)))
                .andExpect(status().isUnauthorized());
    }
}
