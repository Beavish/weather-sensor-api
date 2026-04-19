package com.weatherapi.weather_sensor_api.dto;

import lombok.Value;
import java.time.Instant;
import java.util.Map;

@Value
public class WeatherQueryResponse {
     Map<WeatherMetricEnum, Double> metrics;
     StatTypeEnum stat;
     Instant from;
     Instant to;
}
