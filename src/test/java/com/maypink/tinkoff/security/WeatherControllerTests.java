package com.maypink.tinkoff.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.repositories.WeatherRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WeatherControllerTests extends SpringBootApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    static WeatherRepository weatherRepository;

    private WeatherResource weatherTest;

    private WeatherResource nonExistentWeather;

    private final ObjectWriter objectMapper = new ObjectMapper().writer();

    @BeforeAll
    static void beforeAll() {
        WeatherResource weatherTest = weatherRepository.addWeather(
                new WeatherResource(
                        "Surgut",
                        "HMAO",
                        "Russia",
                        10,
                        10D)
        );

        WeatherResource nonExistentWeather = weatherRepository.addWeather(
                new WeatherResource(
                        "Milan",
                        "Italy",
                        "Italy",
                        10,
                        10D)
        );
    }

    @Test
    @WithMockUser(authorities = "weather:read")
    void userGetAllShouldReturn200() throws Exception {
        final WeatherResource saved = weatherRepository.addWeather(weatherTest);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final var requestBuilder = get("weather/all");

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].city.name").value(weatherTest.name()),
                        jsonPath("$[0].city.country").value(weatherTest.country()),
                        jsonPath("$[0].city.region").value(weatherTest.region()),
                        jsonPath("$[0].city.tempC").value(weatherTest.tempC()),
                        jsonPath("$[0].city.tempF").value(weatherTest.tempF())
                );
    }

    @Test
    @WithMockUser(authorities = "weather:write")
    void adminGetAllShouldReturn200() throws Exception {
        final WeatherResource saved = weatherRepository.addWeather(weatherTest);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final var requestBuilder = get("weather/all");

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].city.name").value(weatherTest.name()),
                        jsonPath("$[0].city.country").value(weatherTest.country()),
                        jsonPath("$[0].city.region").value(weatherTest.region()),
                        jsonPath("$[0].city.tempC").value(weatherTest.tempC()),
                        jsonPath("$[0].city.tempF").value(weatherTest.tempF())
                );
    }

    @Test
    @WithAnonymousUser
    void anonGetAllShouldReturn401() throws Exception {
        final WeatherResource saved = weatherRepository.addWeather(weatherTest);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final var requestBuilder = get("weather/all");

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isUnauthorized()
                );
    }

    @Test
    @WithMockUser(authorities = "weather:read")
    void userGetWeatherShouldReturn200() throws Exception {
        final WeatherResource saved = weatherRepository.addWeather(weatherTest);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final var requestBuilder = get("weather" + "/{name}", saved.name());

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].city.name").value(weatherTest.name()),
                        jsonPath("$[0].city.country").value(weatherTest.country()),
                        jsonPath("$[0].city.region").value(weatherTest.region()),
                        jsonPath("$[0].city.tempC").value(weatherTest.tempC()),
                        jsonPath("$[0].city.tempF").value(weatherTest.tempF())
                );
    }

    @Test
    @WithMockUser(authorities = "weather:write")
    void adminGetWeatherShouldReturn200() throws Exception {
        final WeatherResource saved = weatherRepository.addWeather(weatherTest);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final var requestBuilder = get("weather" + "/{name}", saved.name());

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].city.name").value(weatherTest.name()),
                        jsonPath("$[0].city.country").value(weatherTest.country()),
                        jsonPath("$[0].city.region").value(weatherTest.region()),
                        jsonPath("$[0].city.tempC").value(weatherTest.tempC()),
                        jsonPath("$[0].city.tempF").value(weatherTest.tempF())
                );
    }

    @Test
    @WithAnonymousUser
    void anonGetWeatherShouldReturn401() throws Exception {
        final WeatherResource saved = weatherRepository.addWeather(weatherTest);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final var requestBuilder = get("weather" + "/{name}", saved.name());

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isUnauthorized()
                );
    }

    @Test
    @WithMockUser(authorities = "weather:read")
    void userPostNewWeatherShouldReturn403() throws Exception {
        weatherRepository.addWeather(nonExistentWeather);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final String jsonRequest = objectMapper.writeValueAsString(nonExistentWeather);
        var requestBuilder = post("weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isForbidden(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", notNullValue()),
                        jsonPath("$[0].city.name").value(nonExistentWeather.name()),
                        jsonPath("$[0].city.country").value(nonExistentWeather.country()),
                        jsonPath("$[0].city.region").value(nonExistentWeather.region()),
                        jsonPath("$[0].city.tempC").value(nonExistentWeather.tempC()),
                        jsonPath("$[0].city.tempF").value(nonExistentWeather.tempF())
                );
    }

    @Test
    @WithMockUser(authorities = "weather:write")
    void adminPostNewWeatherShouldReturn201() throws Exception {
        weatherRepository.addWeather(nonExistentWeather);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final String jsonRequest = objectMapper.writeValueAsString(nonExistentWeather);
        var requestBuilder = post("weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", notNullValue()),
                        jsonPath("$[0].city.name").value(nonExistentWeather.name()),
                        jsonPath("$[0].city.country").value(nonExistentWeather.country()),
                        jsonPath("$[0].city.region").value(nonExistentWeather.region()),
                        jsonPath("$[0].city.tempC").value(nonExistentWeather.tempC()),
                        jsonPath("$[0].city.tempF").value(nonExistentWeather.tempF())
                );
    }

    @Test
    @WithAnonymousUser
    void anonPostNewWeatherShouldReturn401() throws Exception {
        weatherRepository.addWeather(nonExistentWeather);
        assertThat(weatherRepository.getAllWeathers()).hasSize(1);
        final String jsonRequest = objectMapper.writeValueAsString(nonExistentWeather);
        var requestBuilder = post("weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isUnauthorized()
                );
    }
}
