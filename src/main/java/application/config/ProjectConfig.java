package application.config;


import application.service.PasswordEncoder;
import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class ProjectConfig {


    private final CustomUserDetailService customUserDetailService;

    //    @SneakyThrows
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestMatcherBinder requestMatcherBinder) {
//        http.cors(AbstractHttpConfigurer::disable);
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests(
//                request -> {
//                    String[] permitsAllUrls = requestMatcherBinder.getPermitsAllUrls();
//                    if (permitsAllUrls != null && permitsAllUrls.length > 0) {
//                        request.requestMatchers(permitsAllUrls).permitAll();
//                    }
//                    Collection<RequestMatcherBinder.AuthorityPair> authorityPairs = requestMatcherBinder.getAuthorityPair();
//                    if (authorityPairs != null && !authorityPairs.isEmpty()) {
//                        for (RequestMatcherBinder.AuthorityPair pair : authorityPairs) {
//                            request.requestMatchers(pair.getUrls())
//                                    .hasAnyAuthority(pair.getAuthorities());
//                        }
//                    }
//                    request.anyRequest().authenticated();
//                }
//        );
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestMatcherBinder requestMatcherBinder) {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                request -> {
                String[] permitsAllUrls = requestMatcherBinder.getPermitsAllUrls();
                if (permitsAllUrls != null && permitsAllUrls.length > 0) {
                    request.requestMatchers(permitsAllUrls).permitAll();
                }
                Collection<RequestMatcherBinder.AuthorityPair> authorityPairs = requestMatcherBinder.getAuthorityPair();
                if (authorityPairs != null && !authorityPairs.isEmpty()) {
                    for (RequestMatcherBinder.AuthorityPair pair : authorityPairs) {
                        request.requestMatchers(pair.getUrls())
                                .hasAnyAuthority(pair.getAuthorities());
                    }
                }
                    request.anyRequest().authenticated();
                }
        );
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(customUserDetailService);
        return provider;
    }

    @Bean
    public RequestMatcherBinder getRequestMatcherBinder() {
        return new RequestMatcherBinderImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
