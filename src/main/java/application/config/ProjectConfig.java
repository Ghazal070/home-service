package application.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration

public class ProjectConfig {


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
                    if (authorityPairs !=null && !authorityPairs.isEmpty()){
                        for (RequestMatcherBinder.AuthorityPair pair:authorityPairs) {
                            request.requestMatchers(pair.getUrls())
                                    .hasAnyAuthority(pair.getAuthorities());
                        }
                    }
                    request.anyRequest().authenticated();
                }
        );
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public RequestMatcherBinder getRequestMatcherBinder() {
        return new CustomRequestMatcherBinder();
    }
}
