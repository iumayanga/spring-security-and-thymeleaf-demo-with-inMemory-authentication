package com.example.thymeleafdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {

		UserDetails john = User.builder()
				.username("john")
				.password("{noop}test123")
				.roles("EMPLOYEE")
				.build();

		UserDetails mary = User.builder()
				.username("mary")
				.password("{noop}test123")
				.roles("EMPLOYEE", "MANAGER")
				.build();

		UserDetails susan = User.builder()
				.username("susan")
				.password("{noop}test123")
				.roles("EMPLOYEE", "ADMIN")
				.build();

		return new InMemoryUserDetailsManager(john, mary, susan);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http
				.authorizeRequests(configurer ->
						configurer
//							.anyRequest().authenticated()
							.antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
							.antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
							.antMatchers("/employees/delete").hasRole("ADMIN")
							.antMatchers("/employees/**").hasRole("EMPLOYEE")
							.antMatchers("/resources/**").permitAll())

				.formLogin(configurer ->
						configurer
								.loginPage("/showMyLoginPage")
								.loginProcessingUrl("/authenticateTheUser")
								.permitAll())

				.logout(configurer ->
						configurer
								.permitAll())

				.exceptionHandling(configurer ->
						configurer
								.accessDeniedPage("/access-denied"))

				.build();
	}
}