package com.weatherapi.weather_sensor_api.mapper;

import com.weatherapi.weather_sensor_api.dto.WeatherReadingRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherReadingResponse;
import com.weatherapi.weather_sensor_api.entity.WeatherReading;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class WeatherReadingMapper {

    public WeatherReading toEntity(WeatherReadingRequest request) {
        if (request == null) {
            return null;
        }

        WeatherReading entity = new WeatherReading();

        entity.setSensorId(request.getSensorId());
        entity.setTemperature(request.getTemperature());
        entity.setHumidity(request.getHumidity());
        entity.setWindSpeed(request.getWindSpeed());

        // mapping naming difference
        entity.setTimestamp(
                request.getTimestamp() != null
                        ? request.getTimestamp()
                        : Instant.now()
        );

        return entity;
    }

    public WeatherReadingResponse toResponse(WeatherReading entity) {
        if (entity == null) {
            return null;
        }

        return new WeatherReadingResponse(
                entity.getSensorId(),
                entity.getTemperature(),
                entity.getHumidity(),
                entity.getWindSpeed(),
                entity.getTimestamp()
        );
    }

}