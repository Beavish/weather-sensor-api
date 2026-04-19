package com.weatherapi.weather_sensor_api.validation;

import com.weatherapi.weather_sensor_api.dto.WeatherQueryRequest;
import com.weatherapi.weather_sensor_api.validation.annotation.ValidTimeRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, WeatherQueryRequest> {

    private static final long MIN_RANGE_HOURS = 24;          // 1 day
    private static final long MAX_RANGE_DAYS = 30;           // 1 month

    @Override
    public boolean isValid(WeatherQueryRequest request, ConstraintValidatorContext context) {

        if (request == null) return true;

        // Case 1: No range provided → valid (latest data logic handled in service)
        if (request.getFrom() == null && request.getTo() == null) {
            return true;
        }

        // Case 2: One missing → invalid
        if (request.getFrom() == null || request.getTo() == null) {
            return buildViolation(context, "'from' and 'to' must both be provided");
        }

        // Case 3: from > to → invalid
        if (request.getFrom().isAfter(request.getTo())) {
            return buildViolation(context, "'from' must be before or equal to 'to'");
        }

        // Case 4: Range too small or too large
        Duration duration = Duration.between(request.getFrom(), request.getTo());

        if (duration.toHours() < MIN_RANGE_HOURS) {
            return buildViolation(context, "Date range must be at least 1 day");
        }

        if (duration.toDays() > MAX_RANGE_DAYS) {
            return buildViolation(context, "Date range must not exceed 30 days");
        }

        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}