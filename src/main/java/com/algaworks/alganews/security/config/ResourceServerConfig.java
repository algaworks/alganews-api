package com.algaworks.alganews.security.config;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	private final SecurityProperties securityProperties;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
			.and()
			.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec(securityProperties.getResourceServer().getTokensSigningKey().getBytes(), 
				"HmacSHA256");
		
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			var authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				authorities = Collections.emptyList();
			}
			
			var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
			Collection<GrantedAuthority> grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);
			
			grantedAuthorities.addAll(authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()));
			
			return grantedAuthorities;
		});
		
		return jwtAuthenticationConverter;
	}
	
}
