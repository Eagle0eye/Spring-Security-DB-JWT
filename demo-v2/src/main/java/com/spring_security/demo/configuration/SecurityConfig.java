package com.spring_security.demo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.spring_security.demo.enums.Permission.*;
import static com.spring_security.demo.enums.Roles.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Un Authenticated Object --> <Authentication Provider> --> Authenticated Object
    private final UserDetailsService userDetailsService;


    private final JWTFilter jwtFilter;
    SecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter)
    {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{


        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->request
                .requestMatchers("/api/auth/**")
                .permitAll()

                      .requestMatchers("/api/v1/users/**").hasAnyRole(ADMIN.name(), USER.name())
                        .requestMatchers(GET,"/api/v1/users").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                        .requestMatchers(POST,"/api/v1/users").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/users").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
                        .requestMatchers(DELETE,"/api/v1/users").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())

                      .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                        .requestMatchers(GET,"/api/v1/admin").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST,"/api/v1/admin").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/admin").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE,"/api/v1/admin").hasAuthority(ADMIN_DELETE.name())
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // 1- First Implementation
    // -------------------------
//        // Disable CSRF-Token
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        // Made any requests authenticated
//        httpSecurity.authorizeHttpRequests(request->request.anyRequest().authenticated());
//        // Enable for web browsers
//        // httpSecurity.formLogin(Customizer.withDefaults());
//        // Enable for other clients like postman
//        httpSecurity.httpBasic(Customizer.withDefaults());
//
//        // every time he tries to make request create new session
//        httpSecurity.sessionManagement(session -> {session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);});
//        // HttpSecurity .build() return SecurityFilterChain

    // 2- Second Implementation
    // ------------------------
    /*
     * httpSecurity.csrf(AbstractHttpConfigurer::disable);
     *        ------------------------------------------------------
     * Customizer<CsrfConfigurer<HttpSecurity>> custCSRF= new Customizer<CsrfConfigurer<HttpSecurity>>() {
     *   @Override
     *    public void customize(CsrfConfigurer<HttpSecurity> httpSecurity) {
     *       httpSecurity.disable();
     *    }
     * };
     * httpSecurity.csrf(custCSRF);
     */


//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user_1 = User
//                .withDefaultPasswordEncoder()
//                .username("yousef")
//                .password("0000")
//                .roles("USER").build();
//
//        UserDetails user_2 = User
//                .withDefaultPasswordEncoder()
//                .username("hanady")
//                .password("0000")
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user_1,user_2);
//    }





}
