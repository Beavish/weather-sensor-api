package com.weatherapi.weather_sensor_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
public class WeatherReadingRequest {

    @NotBlank(message = "sensorId is required")
    private String sensorId;

    private Double temperature;

    @Min(0)
    @Max(100)
    private Double humidity;

    @PositiveOrZero
    private Double windSpeed;

    private Instant timestamp;

    @AssertTrue(message = "At least one metric value must be provided")
    public boolean hasAtLeastOneMetric() {
        return temperature != null || humidity != null || windSpeed != null;
    }
}
