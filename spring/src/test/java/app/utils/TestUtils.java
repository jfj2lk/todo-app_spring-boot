package app.utils;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import app.auth.JwtService;
import app.model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestUtils {
    private final JwtService jwtService;
    private final PasswordUtils passwordUtils;
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * 指定されたユーザーIDのJWTを作成する。
     */
    public String createJwt(Long userId) {
        User user = new User("user1", "email1", passwordUtils.encode("password1"));
        user.setId(userId);
        return jwtService.generateJwt(user);
    }

    /**
     * オブジェクトをJSON文字列に変換する。
     */
    public String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
