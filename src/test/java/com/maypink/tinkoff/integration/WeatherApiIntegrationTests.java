package com.maypink.tinkoff.integration;

import com.maypink.tinkoff.config.WeatherConfigProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherApiIntegrationTests {

    @SpyBean
    WeatherConfigProperties weatherConfigProperties;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getWeatherFromApiReturns200() throws Exception {

        String weatherName = "Tokyo";
        String region = "Tokyo";
        String country = "Japan";

        mvc.perform(get("/weather").queryParam("query", weatherName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(weatherName))
                .andExpect(jsonPath("$.region").value(region))
                .andExpect(jsonPath("$.country").value(country));
    }

    @Test
    public void getWeatherFromApiReturns400() throws Exception {

        String weatherName = "NonExistentName";
        mvc.perform(get("/weather").queryParam("query", weatherName))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("No matching location found."));
    }

    @Test
    public void getWeatherFromApiNoApiKeyReturns403() throws Exception {

        // kind of stub
        Mockito.when(weatherConfigProperties.getApiKey()).thenReturn("NoApiKey");
        String weatherName = "Tokyo";
        mvc.perform(get("/weather").queryParam("query", weatherName))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(2008))
                .andExpect(jsonPath("$.error").value("API key has been disabled."));
    }
}
