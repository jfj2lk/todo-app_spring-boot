package app.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
    // 署名鍵のパス
    private String signInKeyPath = "secret/hs256.key";
    // HMAC用の署名鍵
    private SecretKey hmacSignInKey = createHmacSignInKey();
    // 有効期限
    private Long expirationDate = createExpirationDate();

    /**
     * JWTの作成。
     */
    // TODO:UserDetail実装に変える。
    public String generateJwt(User user) {
        return Jwts
                .builder()
                .subject(String.valueOf(user.getId()))
                .expiration(new Date(System.currentTimeMillis() + this.expirationDate))
                .signWith(this.hmacSignInKey)
                .compact();
    }

    /**
     * JWTの検証。
     */
    public void validateJwt(String jwt) {
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(hmacSignInKey)
                    .build()
                    .parseSignedClaims(jwt).getPayload();
            // 検証に成功した場合は認証情報を設定する
            setAuthInfo(claims);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWTの有効期限が切れています。");
        } catch (RuntimeException e) {
            throw new RuntimeException("JWTの検証中にエラーが発生しました。");
        }
    }

    /**
     * 認証情報を設定する。
     */
    public void setAuthInfo(Claims claims) {
        String subject = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = List.of();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(subject, null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 署名鍵ファイルの値をバイトとして取得。
     */
    public byte[] getSignInKeyBytes() throws RuntimeException {
        try {
            // ファイルからBASE64エンコードされた、署名鍵の値を取得
            ClassPathResource resource = new ClassPathResource(signInKeyPath);
            byte[] bytes = resource.getInputStream().readAllBytes();
            String base64EncodedSignInKey = new String(bytes, StandardCharsets.UTF_8).trim();
            // 署名鍵をデコードして取得したバイト値を返す
            return Decoders.BASE64.decode(base64EncodedSignInKey);
        } catch (IOException e) {
            throw new RuntimeException("署名鍵ファイルの読み込みに失敗しました。", e);
        }
    }

    /**
     * HMAC形式の署名鍵を作成する。
     */
    public SecretKey createHmacSignInKey() throws RuntimeException {
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

        // 1秒のミリ秒
        final long SECONDS_IN_MILLIS = 1000;
        // 1分のミリ秒
        final long MINUTE_IN_MILLIS = SECONDS_IN_MILLIS * MINUTES_IN_SECONDS;
        // 1時間のミリ秒
        final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * HOURS_IN_MINUTES;

        // 有効期限
        final long EXPIRATION_DATE_IN_MILLIS = HOUR_IN_MILLIS * 1;
        return EXPIRATION_DATE_IN_MILLIS;
    }
}
