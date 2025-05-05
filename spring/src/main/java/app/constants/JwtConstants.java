package app.constants;

import org.springframework.stereotype.Component;

@Component
public class JwtConstants {
    // 認証を行わないURLのリスト
    public final String[] permitAllUrls = """
            /api/auth/signup
            /api/auth/login
            /h2/**
            """.trim().split("\\s+");

    // JWTの検証時に返される値
    public enum JwtValidateResult {
        // 検証成功
        SUCCESS,
        // 有効期限切れ
        EXPIRED,
        // 検証失敗
        INVALID
    }
}
