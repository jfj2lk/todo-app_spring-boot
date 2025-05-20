package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.form.user.LoginForm;
import app.form.user.SignUpForm;
import app.model.User;
import app.service.AuthService;
import app.utils.JwtUtils;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private JwtUtils jwtService;

    /**
     * 新規登録する。
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Validated SignUpForm signUpForm) {
        // 新規登録処理を行い、DBに追加されたUser情報を取得する
        final User signedUpUser = authService.signup(signUpForm);
        // JWTトークン発行
        final String jwt = jwtService.generateJwt(signedUpUser);

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("accessToken", jwt),
                Map.entry("message", "新規登録しました。")));
    }

    /**
     * ログインする。
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Validated LoginForm loginForm) {
        // ログイン処理を行い、DBに保存されているUser情報を取得する
        final User loginUser = authService.login(loginForm);
        // JWTトークン発行
        final String jwt = jwtService.generateJwt(loginUser);

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("accessToken", jwt),
                Map.entry("message", "ログインしました。")));
    }

}
