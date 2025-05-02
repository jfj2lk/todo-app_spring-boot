package app.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import app.model.User;
import app.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    /**
     * フィールド
     */
    // 署名鍵のパス
    private String signInKeyPath = "secret/hs256.key";
    // HMAC用の署名鍵
    private SecretKey hmacSignInKey;

    /**
     * コンストラクタ。
     */
    public JwtService() {
        this.hmacSignInKey = createHmacSignInKey();
    }

    /**
     * JWTの生成。
     */
    // TODO:UserDetail実装に変える。
    public String generateJwt(User user) {
        return Jwts
                .builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + createExpirationDate()))
                .signWith(this.hmacSignInKey)
                .compact();
    }

    /**
     * JWTの検証。
     */
    public void validateJwt(String jwt) {
        try {
            Jwts
                    .parser()
                    .verifyWith(hmacSignInKey)
                    .build()
                    .parseSignedClaims(jwt);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, null, e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 署名鍵ファイルの値をバイトとして取得。
     */
    public byte[] getSignInKeyBytes() {
        try {
            // ファイルからBASE64エンコードされた、秘密鍵の値を取得
            ClassPathResource resource = new ClassPathResource(signInKeyPath);
            byte[] bytes = resource.getInputStream().readAllBytes();
            String base64EncodedPrivateKey = new String(bytes, StandardCharsets.UTF_8).trim();
            // 秘密鍵をデコードして取得したバイト値を返す
            return Decoders.BASE64.decode(base64EncodedPrivateKey);
        } catch (IOException e) {
            throw new RuntimeException("署名鍵ファイルの読み込みに失敗しました。", e);
        }
    }

    /**
     * HMAC形式の署名鍵を作成する。
     */
    public SecretKey createHmacSignInKey() {
        // 署名鍵のバイト値
        byte[] signInKeyBytes = getSignInKeyBytes();
        return Keys.hmacShaKeyFor(signInKeyBytes);
    }

    /**
     * JWT有効期限の作成。有効期限をミリ秒で返す。
     */
    public long createExpirationDate() {
        // 1分の秒数
        final long MINUTES_IN_SECONDS = 60;
        // 1時間の分数
        final long HOURS_IN_MINUTES = 60;

        // 1秒のミリ秒数
        final long SECONDS_IN_MILLIS = 1000;
        // 1分のミリ秒
        final long MINUTE_IN_MILLIS = SECONDS_IN_MILLIS * MINUTES_IN_SECONDS;
        // 1時間のミリ秒
        final long HOUR_IN_MILLIS = MINUTES_IN_SECONDS * HOURS_IN_MINUTES;

        // 有効期限
        final long EXPIRATION_DATE_IN_MILLIS = SECONDS_IN_MILLIS * 60;
        return EXPIRATION_DATE_IN_MILLIS;
    }
}
