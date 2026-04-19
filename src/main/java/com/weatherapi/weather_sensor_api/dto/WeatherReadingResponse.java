package com.weatherapi.weather_sensor_api.dto;

import lombok.Data;
import lombok.Value;

import java.time.Instant;

@Value
public class WeatherReadingResponse {
     String sensorId;
     Double temperature;
     Double humidity;
     Double windSpeed;
     Instant timestamp;
}
