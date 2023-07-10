package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.filter.JwtRequestFilter;




// JWT Example
@Configuration
public class SecurityConfiguration {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	// Authentication - who are you?
	@Bean
	protected UserDetailsService userDetailsService() {
		
		return userDetailsService;
	}
	
	// Authorization - what do you want?Ø
	@Bean
	protected SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
		

//        http.cors().and().csrf().disable()
		http.csrf()
		.disable()
        .authorizeRequests()
        //Authentication endpoints
        .antMatchers("/authenticate").permitAll()
		.antMatchers(HttpMethod.POST, "/api/user").permitAll()
		.antMatchers(HttpMethod.GET, "/api/user/*").hasAnyRole("ADMIN","USER") //authorrized users only
		.antMatchers(HttpMethod.DELETE, "/api/user/*").hasRole("ADMIN") //admin only
//        .antMatchers("/api/hello").hasRole("USER") //Testing
        .antMatchers("/swagger-ui/**").permitAll() 
        .antMatchers("/v3/api-docs/**").permitAll()
//        .antMatchers(HttpMethod.GET,"/api/user").hasRole("USER")
//        .antMatchers(HttpMethod.GET,"/api/user/name/**").hasRole("USER")
//        .antMatchers(HttpMethod.PUT,"/api/user").hasRole("USER")
//        .antMatchers(HttpMethod.DELETE,"/api/user").hasRole("USER")   
//        .antMatchers(HttpMethod.POST, "/api/user").permitAll() 
        
        // Order controller config
 		.antMatchers(HttpMethod.GET, "/api/order").hasRole("ADMIN")
 		.antMatchers(HttpMethod.GET, "/api/order/*").hasAnyRole("ADMIN", "USER")
 		.antMatchers(HttpMethod.GET, "/api/order/user/*").hasAnyRole("ADMIN", "USER")
 		.antMatchers(HttpMethod.POST, "/api/order").hasAnyRole("ADMIN", "USER")
 		.antMatchers(HttpMethod.DELETE, "/api/order/*").hasRole("ADMIN")
 		
 		// Product controller config
 		.antMatchers(HttpMethod.GET, "/api/furniture").permitAll()
 		.antMatchers(HttpMethod.GET, "/api/furniture/*").permitAll()
 		.antMatchers(HttpMethod.POST, "/api/furniture").hasRole("ADMIN")
 		.antMatchers(HttpMethod.PUT, "/api/furniture").hasRole("ADMIN")
 		.antMatchers(HttpMethod.DELETE, "/api/furniture/*").hasRole("ADMIN")
 		
// 		
//        .antMatchers(HttpMethod.GET, "/api/furniture").hasRole("USER")
//        .antMatchers(HttpMethod.GET, "/api/furniture/id/**").permitAll() 
//        .antMatchers(HttpMethod.GET, "/api/furniture/product/**").permitAll() 
//        .antMatchers(HttpMethod.POST, "/api/furniture").hasRole("USER") 
//        .antMatchers(HttpMethod.PUT, "/api/furniture").hasRole("ADMIN")
//        .antMatchers(HttpMethod.DELETE, "/api/furniture/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
        //this request will go through many filters, but first it goes through the jwt filter so if a user is logged in they can use their token instead of checking username & password
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
	}
	
	
	// Encoder -> method that will encode/decode all the user passwords
	@Bean
	protected PasswordEncoder encoder() {
		
		// plain text encoder -> won't do any encoding
		//return NoOpPasswordEncoder.getInstance();
		
		// there's many options for password encoding, just pick a algorithm that you like
		return new BCryptPasswordEncoder(); 
	}
	
	// load the encoder & user details service that are needed for spring security to do authentication
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder( encoder() );
		
		return authProvider;
	}
	
	// can autowire and access the authentication manager (manages authentication login) of our project)
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
}
