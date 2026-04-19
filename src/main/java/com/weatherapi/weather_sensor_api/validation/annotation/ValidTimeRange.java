package com.weatherapi.weather_sensor_api.validation.annotation;

import com.weatherapi.weather_sensor_api.validation.TimeRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // applied at class level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeRangeValidator.class)
@Documented
public @interface ValidTimeRange {

    String message() default "'from' must be before or equal to 'to'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}