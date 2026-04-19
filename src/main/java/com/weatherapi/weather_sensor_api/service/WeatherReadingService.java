package com.weatherapi.weather_sensor_api.service;

import com.weatherapi.weather_sensor_api.dto.WeatherQueryRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherQueryResponse;
import com.weatherapi.weather_sensor_api.dto.WeatherReadingRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherReadingResponse;
import org.springframework.stereotype.Component;

@Component
public interface WeatherReadingService {

    WeatherReadingResponse createReading(WeatherReadingRequest request);

    WeatherQueryResponse queryReadings(WeatherQueryRequest request);
}