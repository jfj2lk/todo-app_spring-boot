package app.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF要求無効化
                .csrf(csrf -> csrf.disable())
                // iframe埋め込み許可（H2コンソール確認用）
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // 全てのリクエストを許可
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/**").permitAll());
        return http.build();
    }
}
