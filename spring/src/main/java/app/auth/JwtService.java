package app.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import app.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // 秘密鍵
    // TODO: opensslで作成した秘密鍵に変える。
    private final String SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9supersecretrandomstringforjwt";
    // 署名に使用するキー
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    // 有効期限
    private final long expirationDate = 1000 * 60 * 60;

    // JWTトークンの生成
    // TODO:UserDetail実装に変える。
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(String.valueOf(user
                        .getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationDate))
                .signWith(key)
                .compact();
    }
}
