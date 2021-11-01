package eu.kamilsikora.financial.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.kamilsikora.financial.api.configuration.auth.AuthenticationFailureHandler;
import eu.kamilsikora.financial.api.configuration.auth.AuthenticationSuccessHandler;
import eu.kamilsikora.financial.api.configuration.auth.JsonAuthenticationFilter;
import eu.kamilsikora.financial.api.configuration.auth.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST_OPENAPI = {
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/webjars/**",
            "favicon.ico",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final String secret;

    public SecurityConfiguration(final AuthenticationFailureHandler failureHandler,
                                 final AuthenticationSuccessHandler successHandler,
                                 final UserDetailsService userDetailsService,
                                 final ObjectMapper objectMapper,
                                 @Value("${jwt.secret}") final String secret) {
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.secret = secret;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO: add some more logic
        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/activate/**").permitAll()
                .antMatchers(WHITELIST_OPENAPI).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jsonAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        final JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter(objectMapper);
        jsonAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        jsonAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        jsonAuthenticationFilter.setFilterProcessesUrl("/login");
        return jsonAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(super.authenticationManager(), userDetailsService, secret);
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
