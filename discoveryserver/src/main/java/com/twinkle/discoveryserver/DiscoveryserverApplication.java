package com.twinkle.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // bật server Eureka cua spring cloud
public class DiscoveryserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryserverApplication.class, args);
	}

}
