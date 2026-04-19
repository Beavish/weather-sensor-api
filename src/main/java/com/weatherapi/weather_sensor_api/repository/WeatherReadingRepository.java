package com.weatherapi.weather_sensor_api.repository;

import com.weatherapi.weather_sensor_api.entity.WeatherReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface WeatherReadingRepository extends JpaRepository<WeatherReading, Long> {

    List<WeatherReading> findByTimestampBetween(Instant from, Instant to);

    List<WeatherReading> findBySensorIdInAndTimestampBetween(
            List<String> sensorIds,
            Instant from,
            Instant to
    );
}
