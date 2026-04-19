package com.weatherapi.weather_sensor_api.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "weather_readings",
        indexes = {
                @Index(name = "idx_sensor_timestamp", columnList = "sensorId, timestamp")
        }
)
public class WeatherReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sensorId;

    @Column
    private Double temperature;

    @Column
    private Double humidity;

    @Column
    private Double windSpeed;

    @Column(nullable = false)
    private Instant timestamp;

}