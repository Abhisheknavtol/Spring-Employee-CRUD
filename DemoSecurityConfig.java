package com.example.jhajeecodes.Employee.Management.System.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig implements ErrorPageRegistrar {

	// add support for JDBC ... no more hardcoded users :-)

	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {

		return new JdbcUserDetailsManager(dataSource);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(configurer -> {
			try {
				configurer.requestMatchers("/").hasRole("EMPLOYEE").requestMatchers("/leaders/**").hasRole("MANAGER")
						.requestMatchers("/systems/**").hasRole("ADMIN").anyRequest().authenticated();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).formLogin(form -> form.loginPage("/showMyLoginPage").loginProcessingUrl("/authenticateTheUser").permitAll())
				.logout(logout -> logout.permitAll().invalidateHttpSession(true).clearAuthentication(true)

				)

				.exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));

		return http.build();
	}

	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsManager() {
	 * 
	 * UserDetails john = User.builder() .username("john")
	 * .password("{noop}test123") .roles("EMPLOYEE") .build();
	 * 
	 * UserDetails mary = User.builder() .username("mary")
	 * .password("{noop}test123") .roles("EMPLOYEE", "MANAGER") .build();
	 * 
	 * UserDetails susan = User.builder() .username("susan")
	 * .password("{noop}test123") .roles("EMPLOYEE", "MANAGER", "ADMIN") .build();
	 * 
	 * return new InMemoryUserDetailsManager(john, mary, susan); }
	 */

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
		registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"));

	}
}