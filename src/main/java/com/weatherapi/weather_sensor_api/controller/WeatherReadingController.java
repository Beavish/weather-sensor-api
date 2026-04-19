package com.weatherapi.weather_sensor_api.controller;

import com.weatherapi.weather_sensor_api.dto.WeatherQueryRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherQueryResponse;
import com.weatherapi.weather_sensor_api.dto.WeatherReadingRequest;
import com.weatherapi.weather_sensor_api.dto.WeatherReadingResponse;
import com.weatherapi.weather_sensor_api.service.WeatherReadingService;
import com.weatherapi.weather_sensor_api.service.WeatherReadingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather-readings")
@RequiredArgsConstructor
public class WeatherReadingController {


    private final WeatherReadingService weatherReadingService;

    /**
     * Creates a new weather reading from a sensor.
     *
     * <p>This endpoint accepts raw sensor data and persists it as a single
     * weather reading record.</p>
     *
     * <p><b>Data assumptions:</b></p>
     * <ul>
     *   <li>Timestamp is stored as UTC (ISO-8601 Instant). If not provided, the server may assign the current UTC time.</li>
     *   <li>Temperature is expected in degrees Celsius (°C).</li>
     *   <li>Humidity is expressed as a percentage (0–100).</li>
     *   <li>Wind speed is expressed in kilometers per hour (km/h).</li>
     * </ul>
     *
     * <p><b>Validation rules:</b></p>
     * <ul>
     *   <li>sensorId is required</li>
     *   <li>At least one metric (temperature, humidity, or wind speed) must be provided</li>
     * </ul>
     *
     * @param request the weather reading payload from a sensor
     * @return the persisted weather reading including generated ID and timestamp
     */
    @PostMapping
    public ResponseEntity<WeatherReadingResponse> createReading(
            @Valid @RequestBody WeatherReadingRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(weatherReadingService.createReading(request));
    }

    /**
     * Queries aggregated weather data across one or more sensors within a time range.
     *
     * <p>This endpoint retrieves weather readings and applies aggregation functions
     * (MIN, MAX, AVG, SUM) over the selected metrics.</p>
     *
     * <p><b>Data assumptions:</b></p>
     * <ul>
     *   <li>All timestamps are stored and returned in UTC (ISO-8601 Instant).</li>
     *   <li>Temperature is measured in degrees Celsius (°C).</li>
     *   <li>Humidity is expressed as a percentage (0–100).</li>
     *   <li>Wind speed is measured in kilometers per hour (km/h).</li>
     * </ul>
     *
     * <p><b>Query behaviour:</b></p>
     * <ul>
     *   <li>If sensorIds is not provided, all sensors are included.</li>
     *   <li>Results are filtered by the provided time range (from/to).</li>
     *   <li>If no time range is provided, the most recent data is used.</li>
     *   <li>Each metric is aggregated independently using the selected statistical operation.</li>
     * </ul>
     *
     * @param request query parameters including sensors, metrics, stat type, and time range
     * @return aggregated weather metrics for the requested filters
     */
    @PostMapping("/query")
    public ResponseEntity<WeatherQueryResponse> queryReadings(
            @Valid @RequestBody WeatherQueryRequest request) {
        return ResponseEntity.ok(weatherReadingService.queryReadings(request));
    }
}

