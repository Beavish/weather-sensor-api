package com.weatherapi.weather_sensor_api.dto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.ToDoubleFunction;

@RequiredArgsConstructor
public enum StatTypeEnum {

    AVG(list -> list.stream().mapToDouble(d -> d).average().orElse(0.0)),
    MIN(list -> list.stream().mapToDouble(d -> d).min().orElse(0.0)),
    MAX(list -> list.stream().mapToDouble(d -> d).max().orElse(0.0)),
    SUM(list -> list.stream().mapToDouble(d -> d).sum());

    private final ToDoubleFunction<List<Double>> operation;

    public double apply(List<Double> values) {
        return operation.applyAsDouble(values);
    }
}
