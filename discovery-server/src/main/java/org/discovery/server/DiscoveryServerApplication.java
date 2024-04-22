package org.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
	public static void main(String [] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(DiscoveryServerApplication.class,args);
	}
}
