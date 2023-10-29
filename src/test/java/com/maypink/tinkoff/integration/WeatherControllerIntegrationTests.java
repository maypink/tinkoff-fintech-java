package com.maypink.tinkoff.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WeatherControllerIntegrationTests extends SpringBootApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void add201() throws Exception {
        String weatherName = "Milan";

        mockMvc.perform(post("/weather/new").contentType(MediaType.APPLICATION_JSON)
                        .content(weatherName)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    // insert TestCity in schema to make this test independent of 'testAddMethod'
    @Test
    void get200() throws Exception {
        String weatherName = "TestCity";

        mockMvc.perform(get("/weather/" + weatherName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(weatherName));
    }

    @Test
    void get404() throws Exception {
        String weatherName = "NonExistentCity";

        mockMvc.perform(get("/weather/" + weatherName))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllWeathersMethod200() throws Exception {

        mockMvc.perform(get("/weather/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
