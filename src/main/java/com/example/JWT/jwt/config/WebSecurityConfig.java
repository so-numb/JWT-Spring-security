package com.example.JWT.jwt.config;

import com.example.JWT.jwt.security.Impl.UserDetailsServiceImpl;
import com.example.JWT.jwt.security.jwt.AuthEntryPointJwt;
import com.example.JWT.jwt.security.jwt.AuthTokenFilterJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springdoc.core.utils.Constants.SWAGGER_UI_PATH;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilterJwt authenticationJwtTokenFilter() {
        return new AuthTokenFilterJwt();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsServiceImpl);
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        //Configuration
//        httpSecurity.csrf(csrf-> csrf.disable())
//                .cors(cors->cors.disable())
//                .authorizeHttpRequests(
//                        auth->auth
//                                .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
//                                .requestMatchers("/api/auth/**").permitAll()
//                                .requestMatchers("/").permitAll()
//                              //  .requestMatchers("http://localhost:8080/swagger-ui.html").permitAll()
//                                .requestMatchers("/swagger-ui/index.html").permitAll()
//                                .requestMatchers("/swagger-ui.html", "/configuration/**", "/swagger-resources/**",
//                                        "/v2/api-docs", "/webjars/**").permitAll()
//                                .requestMatchers("/" + SWAGGER_UI_PATH + "/**", // Swagger UI HTML and resources
//                                        "/v3/api-docs/**", // OpenAPI v3 API-docs
//                                        "/swagger-ui/**", // Swagger UI webjars
//                                        "/swagger-ui.html" // Swagger UI index page (HTML)
//                                ).permitAll()
//
//                                .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex->ex.authenticationEntryPoint(unauthorizedHandler))
//                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        httpSecurity.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/login");
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )


                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/*");
    }



//    @Bean
//    UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        UserDetails user = User.withUsername("javainuse").password("javainuse").authorities("read").build();
//        userDetailsService.createUser(user);
//        return userDetailsService;
//    }

}
