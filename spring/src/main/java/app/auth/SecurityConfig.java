package app.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtInfo jwtInfo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF要求無効化
                .csrf(csrf -> csrf.disable())
                // iframeの埋め込みを許可（H2コンソール確認用）
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // 許可URL以外にアクセス制限をかける
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(jwtInfo.permitAllUrls).permitAll()
                                .anyRequest().authenticated())
                // JWT認証を追加
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
