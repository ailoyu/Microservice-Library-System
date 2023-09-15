package com.twinkle.notificationservice.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfiguration {
	
	@Bean // Dùng để call API
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	
	
	@Bean // Config Circuit breaker
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
										// Nếu request quá 3s, circuit breaker ngắt kết nối
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.custom()
						.slidingWindowSize(10) // gọi 10 requests
						.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
						.minimumNumberOfCalls(5) // ít nhất 5 request circuit breaker hoạt động
						.failureRateThreshold(50) // tỷ lệ lỗi 50%, circuit breaker bắt đầu mở
						.build()
						).build()
				);
	}
	
}
