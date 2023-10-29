package com.maypink.tinkoff.unit;

import com.maypink.tinkoff.controllers.WeatherController;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import com.maypink.tinkoff.dto.CurrentDto;
import com.maypink.tinkoff.dto.LocationDto;
import com.maypink.tinkoff.dto.WeatherApiResponse;
import com.maypink.tinkoff.exception.customException.WeatherDuplicateException;
import com.maypink.tinkoff.services.WeatherServiceImpl;
import com.maypink.tinkoff.exception.customException.WeatherNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTests {

    @MockBean
    WeatherServiceImpl weatherServiceImpl;

    @SpyBean
    WeatherController weatherController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getReturns200() throws Exception {
        String weatherName = "name";
        WeatherResource weatherResource = getWeatherWithName(weatherName);
        Mockito.when(weatherServiceImpl.getWeatherByName(weatherName)).thenReturn(List.of(weatherResource));

        mockMvc.perform(get("/weather/" + weatherName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(weatherResource.name()))
                .andExpect(jsonPath("$.region").value(weatherResource.region()))
                .andExpect(jsonPath("$.country").value(weatherResource.country()))
                .andExpect(jsonPath("$.tempC").value(weatherResource.tempC()))
                .andExpect(jsonPath("$.tempF").value(weatherResource.tempF()));
    }

    // to practice spy and stub
    @Test
    public void getThrowsWeatherNotFoundException() {
        String weatherName = "name";
        // kind of stub since there is no possibility to implement full stub class
        doThrow(new WeatherNotFoundException("Weather with specified name not found")).when(weatherServiceImpl).getWeather(weatherName);

        assertThrows(WeatherNotFoundException.class, ()->{weatherController.get(weatherName);} );
    }

    @Test
    public void getAllWeathersReturns200() throws Exception {

        Mockito.when(weatherServiceImpl.getAllWeathers()).thenReturn(getWeatherResources());

        mockMvc.perform(get("/weather/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void addReturns201() throws Exception {
        String weatherName = "Tokyo";
        WeatherResource weatherResource = getWeatherWithName(weatherName);
        WeatherApiResponse weatherApiResponse = new WeatherApiResponse(new LocationDto(weatherName), new CurrentDto());
        Mockito.when(weatherServiceImpl.getWeather(weatherName)).thenReturn(weatherApiResponse);
        Mockito.when(weatherServiceImpl.add(weatherResource)).thenReturn(weatherResource);

        mockMvc.perform(post("/weather/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(weatherName)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(weatherResource.name()))
                .andExpect(jsonPath("$.region").value(weatherResource.region()))
                .andExpect(jsonPath("$.country").value(weatherResource.country()))
                .andExpect(jsonPath("$.tempC").value(weatherResource.tempC()))
                .andExpect(jsonPath("$.tempF").value(weatherResource.tempF()));
    }

    @Test
    public void addThrowsWeatherDuplicateException() throws Exception {
        String weatherName = "NonExistentName";
        WeatherResource weatherResource = getWeatherWithName(weatherName);
        WeatherApiResponse weatherApiResponse = new WeatherApiResponse(new LocationDto(weatherName), new CurrentDto());
        Mockito.when(weatherServiceImpl.getWeather(weatherName)).thenReturn(weatherApiResponse);
        Mockito.when(weatherServiceImpl.add(weatherResource)).thenThrow(new WeatherDuplicateException("Attempt to insert duplicate of Weather."));

        mockMvc.perform(post("/weather/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(weatherName)
                        .characterEncoding("utf-8"))
                        .andExpect(jsonPath("$.status").value(409))
                        .andExpect(jsonPath("$.error").value("Attempt to insert duplicate of Weather."));
    }

    private List<WeatherResource> getWeatherResources(){
        WeatherResource weatherResource1 = getWeatherWithName("name");
        WeatherResource weatherResource2 = getWeatherWithName("name2");
        return List.of(weatherResource1, weatherResource2);
    }

    private WeatherResource getWeatherWithName(String name){
        return new WeatherResource(name, "region", "country", 0, 0D);
    }
}
