package app;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.auth.JwtService;
import app.model.User;
import app.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestUtils {
    private final JwtService jwtService;
    private final UserService userService;

    /**
     * ユーザーID1のJWTを作成する。
     */
    public String createJwt() {
        User user = new User("user1", "email1", userService.encryptPassword("password1"));
        user.setId(1L);
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
