package com.weatherapi.weather_sensor_api.dto;

import com.weatherapi.weather_sensor_api.validation.annotation.ValidTimeRange;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
@ValidTimeRange
public class WeatherQueryRequest {

   List<String> sensorIds;

   @NotEmpty(message = "At least one metric must be provided")
   List<WeatherMetricEnum> metrics;

   StatTypeEnum stat;

   Instant from;

   Instant to;
}
