package com.weatherapi.weather_sensor_api;

import com.weatherapi.weather_sensor_api.dto.StatTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatTypeEnumTest {

    @Test
    void shouldCalculateAverage() {
        List<Double> values = List.of(10.0, 20.0, 30.0);

        double result = StatTypeEnum.AVG.apply(values);

        assertEquals(20.0, result);
    }

    @Test
    void shouldCalculateMin() {
        List<Double> values = List.of(10.0, 20.0, 30.0);

        double result = StatTypeEnum.MIN.apply(values);

        assertEquals(10.0, result);
    }

    @Test
    void shouldCalculateMax() {
        List<Double> values = List.of(10.0, 20.0, 30.0);

        double result = StatTypeEnum.MAX.apply(values);

        assertEquals(30.0, result);
    }

    @Test
    void shouldCalculateSum() {
        List<Double> values = List.of(10.0, 20.0, 30.0);

        double result = StatTypeEnum.SUM.apply(values);

        assertEquals(60.0, result);
    }
}