package com.weatherapi.weather_sensor_api.mapper;

import com.weatherapi.weather_sensor_api.dto.WeatherMetricEnum;
import com.weatherapi.weather_sensor_api.dto.WeatherQueryRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherQueryResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class WeatherQueryMapper {

    public WeatherQueryResponse toResult(
            Map<WeatherMetricEnum, Double> metrics,
            WeatherQueryRequest request
    ) {
        return new WeatherQueryResponse(
                metrics,
                request.getStat(),
                request.getFrom(),
                request.getTo()
        );
    }
}