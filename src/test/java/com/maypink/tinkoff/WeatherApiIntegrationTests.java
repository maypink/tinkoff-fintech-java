package com.maypink.tinkoff;

import com.maypink.tinkoff.exception.ResponseWeatherErrorException;
import com.maypink.tinkoff.exception.customException.WeatherNotFoundException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application.properties")
public class WeatherApiIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetWeatherFromApiWithExistentName() throws Exception {

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
    public void testGetWeatherFromApiWithNonExistentName() throws Exception {

        String weatherName = "NonExistentName";
        mvc.perform(get("/weather").queryParam("query", weatherName))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("No matching location found."));
    }
}
