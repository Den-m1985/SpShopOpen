package ru.spshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spshop.dto.UserDTO;
import ru.spshop.repositories.UserRepository;
import ru.spshop.service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TokenControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterService registerService;

    private final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String email = "john.doe@example.com";
    private final String password = "password";
    private final String baseEndpoint = "/v1/tokens";
    private final String endpointLogin = "/v1/users/login";
    private final UserDTO userDTO = new UserDTO(email, password);

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        registerService.registerUser(userDTO, servletResponse);
    }

    @Test
    void getNewAccessToken_ShouldReturnUAccessDeny() throws Exception {
        mockMvc.perform(post(baseEndpoint + "/token"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getNewAccessToken_ShouldReturnUnauthorized_WhenRefreshTokenMissing() throws Exception {
        mockMvc.perform(post(baseEndpoint + "/token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getNewAccessToken_ShouldReturnAccessToken_WhenValidRefreshToken() throws Exception {
        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post(endpointLogin)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isOk()).andReturn();

        String setCookieHeader = loginResult.getResponse().getHeader(HttpHeaders.SET_COOKIE);
        assertNotNull(setCookieHeader);
        String refreshToken = setCookieHeader.split("=")[1].split(";")[0];

        mockMvc.perform(post(baseEndpoint + "/token")
                        .cookie(new Cookie("refreshToken", refreshToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @WithMockUser
    void getNewAccessToken_ShouldReturn401_WhenNoRefreshToken() throws Exception {
        mockMvc.perform(post(baseEndpoint + "/token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getNewAccessToken_ShouldReturn401_WhenInvalidRefreshToken() throws Exception {
        Cookie badCookie = new Cookie("refreshToken", "invalid_token");

        mockMvc.perform(post(baseEndpoint + "/token")
                        .cookie(badCookie))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.description").value("The JWT signature is invalid"));
    }

}
