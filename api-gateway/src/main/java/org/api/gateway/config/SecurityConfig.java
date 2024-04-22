package org.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	 @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:default}")
	 private String jwkSetUri;
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
						  .authorizeExchange(exchange -> exchange
								  .pathMatchers("/eureka/**")
								  .permitAll()
								  .anyExchange()
								  .authenticated())
//						  .oauth2ResourceServer().jwt();
						  .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
		return serverHttpSecurity.build();
	}
	
	 @Bean
	 public ReactiveJwtDecoder jwtDecoder() {
		 return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
	 }
}
