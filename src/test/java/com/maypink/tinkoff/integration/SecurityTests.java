package com.maypink.tinkoff.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTests extends SpringBootApplicationTests{

    @Autowired
    MockMvc mockMvc;

    private final ObjectWriter objectMapper = new ObjectMapper().writer();

    @Test
    @WithAnonymousUser
    void registerUser() throws Exception {
        return;
//        weatherRepository.addWeather(nonExistentWeather);
//        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
//        final String jsonRequest = objectMapper.writeValueAsString(nonExistentWeather);
//        var requestBuilder = post("register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest);
//
//        mockMvc.perform(requestBuilder)
//
//                .andExpectAll(
//                        status().isUnauthorized()
//                );
    }
}
