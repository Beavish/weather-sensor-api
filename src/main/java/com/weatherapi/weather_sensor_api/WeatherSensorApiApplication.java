package com.weatherapi.weather_sensor_api;

import com.weatherapi.weather_sensor_api.entity.WeatherReading;
import com.weatherapi.weather_sensor_api.repository.WeatherReadingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class WeatherSensorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherSensorApiApplication.class, args);
	}

	@Bean
	CommandLineRunner seedDatabase(WeatherReadingRepository repository) {
		return args -> populateDummyData(repository);
	}

	private void populateDummyData(WeatherReadingRepository repository) {

		WeatherReading r1 = new WeatherReading();
		r1.setSensorId("sensor-1");
		r1.setTemperature(18.5);
		r1.setHumidity(65.0);
		r1.setWindSpeed(12.0);
		r1.setTimestamp(Instant.now().minusSeconds(3600));

		WeatherReading r2 = new WeatherReading();
		r2.setSensorId("sensor-1");
		r2.setTemperature(20.0);
		r2.setHumidity(60.0);
		r2.setWindSpeed(10.0);
		r2.setTimestamp(Instant.now().minusSeconds(1800));

		WeatherReading r3 = new WeatherReading();
		r3.setSensorId("sensor-2");
		r3.setTemperature(15.0);
		r3.setHumidity(70.0);
		r3.setWindSpeed(8.0);
		r3.setTimestamp(Instant.now().minusSeconds(7200));

		repository.save(r1);
		repository.save(r2);
		repository.save(r3);
	}
}
