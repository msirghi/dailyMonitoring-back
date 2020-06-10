package com.example.dailymonitoring.security;

import com.example.dailymonitoring.configs.filters.JwtRequestFilter;
import com.example.dailymonitoring.services.impl.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Profile("prod")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final MyUserDetailsService myUserDetailsService;

  private final JwtRequestFilter jwtRequestFilter;

  public WebSecurityConfig(
      MyUserDetailsService myUserDetailsService,
      JwtRequestFilter jwtRequestFilter) {
    this.myUserDetailsService = myUserDetailsService;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.cors();
    http.headers().frameOptions().disable();

    http
        .authorizeRequests()
        .antMatchers("/authenticate",
            "/createUser",
            "/renewToken",
            "/h2-console/**",
            "/admin/**",
            "/actuator/**",
            "/instances/**",
            "/images/**",
            "/registrationConfirm/**").permitAll()
        .antMatchers(HttpMethod.POST, "/users").permitAll()
        .anyRequest().authenticated();

    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.exceptionHandling();

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myUserDetailsService);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
