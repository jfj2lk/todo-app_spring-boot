package app.utils;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.auth.JwtService;
import app.model.User;
import app.service.AuthService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestUtils {
    private final JwtService jwtService;
    private final AuthService userService;

    /**
     * 指定されたユーザーIDのJWTを作成する。
     */
    public String createJwt(Long userId) {
        User user = new User("user1", "email1", userService.encryptPassword("password1"));
        user.setId(userId);
        return jwtService.generateJwt(user);
    }

    /**
     * オブジェクトをJSON文字列に変換する。
     */
    public String toJson(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
