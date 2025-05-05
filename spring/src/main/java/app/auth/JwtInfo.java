package app.auth;

import org.springframework.stereotype.Component;

@Component
public class JwtInfo {

    // 認証を行わないURLのリスト
    public final String[] permitAllUrls = """
            /api/auth/signup
            /api/auth/login
            /h2/**
            """.trim().split("\\s+");
}
