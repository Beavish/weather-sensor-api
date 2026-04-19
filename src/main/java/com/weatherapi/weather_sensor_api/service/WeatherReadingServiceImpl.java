package com.weatherapi.weather_sensor_api.service;

import com.weatherapi.weather_sensor_api.dto.*;
import com.weatherapi.weather_sensor_api.entity.WeatherReading;
import com.weatherapi.weather_sensor_api.mapper.WeatherQueryMapper;
import com.weatherapi.weather_sensor_api.mapper.WeatherReadingMapper;
import com.weatherapi.weather_sensor_api.repository.WeatherReadingRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherReadingServiceImpl implements WeatherReadingService{

    private final WeatherReadingRepository weatherReadingRepository;
    private final WeatherReadingMapper weatherReadingMapper;
    private final WeatherQueryMapper weatherQueryMapper;

    @Override
    public WeatherReadingResponse createReading(WeatherReadingRequest request) {
        WeatherReading entity = weatherReadingMapper.toEntity(request);
        WeatherReading saved = weatherReadingRepository.save(entity);
        return weatherReadingMapper.toResponse(saved);
    }

    @Override
    public WeatherQueryResponse queryReadings(@Valid WeatherQueryRequest weatherQueryRequest) {

        Instant[] range = resolveTimeRange(weatherQueryRequest);
        Instant from = range[0];
        Instant to = range[1];

        List<WeatherReading> weatherReadings = resolveWeatherReadings(weatherQueryRequest, from, to);

        return weatherQueryMapper.toResult(aggregateWeatherReadings(weatherReadings, weatherQueryRequest), weatherQueryRequest);

    }

    private Instant[] resolveTimeRange(WeatherQueryRequest request) {

        Instant from = request.getFrom();
        Instant to = request.getTo();

        if (from == null && to == null) {
            to = Instant.now();
            from = to.minus(Duration.ofDays(1));
        }

        return new Instant[]{from, to};
    }

    private List<WeatherReading> resolveWeatherReadings(WeatherQueryRequest weatherQueryRequest, Instant from, Instant to) {
        List<WeatherReading> weatherReadings;
        if (weatherQueryRequest.getSensorIds() == null || weatherQueryRequest.getSensorIds().isEmpty()) {
            weatherReadings = weatherReadingRepository.findByTimestampBetween(from, to);
        } else {
            weatherReadings = weatherReadingRepository
                    .findBySensorIdInAndTimestampBetween(weatherQueryRequest.getSensorIds(), from, to);
        }
        return weatherReadings;
    }

    private Map<WeatherMetricEnum, Double> aggregateWeatherReadings(
            List<WeatherReading> weatherReadings,
            WeatherQueryRequest weatherQueryRequest) {

        return
                weatherQueryRequest.getMetrics().stream()
                        .distinct()
                        .collect(Collectors.toMap(
                                metric -> metric,
                                metric -> {
                                    List<Double> values = weatherReadings.stream()
                                            .map(metric::extract)
                                            .filter(Objects::nonNull)
                                            .toList();

                                    return weatherQueryRequest.getStat().apply(values);
                                }
                        ));

    }

}
