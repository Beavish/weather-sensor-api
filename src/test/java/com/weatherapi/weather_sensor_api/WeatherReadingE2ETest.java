package com.weatherapi.weather_sensor_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.weather_sensor_api.dto.WeatherMetricEnum;
import com.weatherapi.weather_sensor_api.dto.WeatherQueryRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherReadingRequest;
import com.weatherapi.weather_sensor_api.dto.StatTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WeatherReadingE2ETest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAndQueryWeatherReading() throws Exception {

        // -------------------------
        // 1. CREATE WEATHER READING
        // -------------------------
        WeatherReadingRequest createRequest = new WeatherReadingRequest();
        createRequest.setSensorId("sensor-1");
        createRequest.setTemperature(20.0);
        createRequest.setHumidity(60.0);
        createRequest.setWindSpeed(10.0);
        createRequest.setTimestamp(Instant.now());

        mockMvc.perform(post("/api/v1/weather-readings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        // -------------------------
        // 2. QUERY WEATHER DATA
        // -------------------------
// -------------------------
// 2. QUERY WEATHER DATA
// -------------------------
        WeatherQueryRequest queryRequest =
                new WeatherQueryRequest(
                        List.of("sensor-1"),
                        List.of(WeatherMetricEnum.TEMPERATURE),
                        StatTypeEnum.AVG,
                        Instant.now().minusSeconds(60 * 60 * 24 + 1), // 24h + 1 sec
                        Instant.now()
                );

        mockMvc.perform(post("/api/v1/weather-readings/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(queryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metrics.TEMPERATURE").exists());
    }

    @Test
    void shouldCreateAndFailQueryWeatherReading() throws Exception {

        // -------------------------
        // 1. CREATE WEATHER READING
        // -------------------------
        WeatherReadingRequest createRequest = new WeatherReadingRequest();
        createRequest.setSensorId("sensor-1");
        createRequest.setTemperature(20.0);
        createRequest.setHumidity(60.0);
        createRequest.setWindSpeed(10.0);
        createRequest.setTimestamp(Instant.now());

        mockMvc.perform(post("/api/v1/weather-readings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        // -------------------------
        // 2. QUERY WEATHER DATA
        // -------------------------
// -------------------------
// 2. QUERY WEATHER DATA
// -------------------------
        WeatherQueryRequest queryRequest =
                new WeatherQueryRequest(
                        List.of("sensor-1"),
                        List.of(WeatherMetricEnum.TEMPERATURE),
                        StatTypeEnum.AVG,
                        Instant.now(), // 24h + 1 sec
                        Instant.now()
                );

        mockMvc.perform(post("/api/v1/weather-readings/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(queryRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.metrics.TEMPERATURE").doesNotExist());
    }
}