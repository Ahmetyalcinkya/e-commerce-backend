package com.workintech.Ecommerce.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.workintech.Ecommerce.util.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    public SecurityConfig(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.getPublicKey())
                .privateKey(rsaKeyProperties.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000/", "https://e-commerce-site-steel.vercel.app/"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION ,HttpHeaders.CONTENT_TYPE));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.cors().configurationSource(corsConfigurationSource());
        return security
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/roles").permitAll(); // +

                    auth.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN");

                    auth.requestMatchers(HttpMethod.GET,"/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();

                    auth.requestMatchers(HttpMethod.GET,"/categories").permitAll();
                    auth.requestMatchers("/categories/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STORE");// will be added to frontend

                    auth.requestMatchers(HttpMethod.GET,"/products/").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/products/").hasAnyAuthority("ROLE_ADMIN", "ROLE_STORE");
                    auth.requestMatchers(HttpMethod.GET,"/products/name/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
                    auth.requestMatchers(HttpMethod.GET,"/products/id/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/products/**").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE,"/products/id/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STORE");

                    auth.requestMatchers(HttpMethod.GET,"/user/address").hasAuthority("ROLE_USER");
                    auth.requestMatchers(HttpMethod.POST,"/user/address").hasAuthority("ROLE_USER");
                    auth.requestMatchers(HttpMethod.GET,"/user/address/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");

                    auth.requestMatchers(HttpMethod.GET,"/user/billing-address").hasAuthority("ROLE_USER");
                    auth.requestMatchers(HttpMethod.POST,"/user/billing-address").hasAuthority("ROLE_USER");
                    auth.requestMatchers(HttpMethod.GET,"/user/billing-address/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");

                    auth.requestMatchers("/swagger-ui/**").permitAll(); //TODO Failed to load remote configuration
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }
}
