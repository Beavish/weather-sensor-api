package com.weatherapi.weather_sensor_api.dto;

import com.weatherapi.weather_sensor_api.entity.WeatherReading;

public enum WeatherMetricEnum {

     TEMPERATURE(WeatherReading::getTemperature),
     HUMIDITY(WeatherReading::getHumidity),
     WIND_SPEED(WeatherReading::getWindSpeed);

     private final java.util.function.Function<WeatherReading, Double> extractor;

     WeatherMetricEnum(java.util.function.Function<WeatherReading, Double> extractor) {
          this.extractor = extractor;
     }

     public Double extract(WeatherReading reading) {
          return extractor.apply(reading);
     }
}