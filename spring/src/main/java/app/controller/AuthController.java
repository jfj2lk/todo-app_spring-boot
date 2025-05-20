package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.auth.JwtService;
import app.form.user.LoginForm;
import app.form.user.SignUpForm;
import app.model.User;
import app.service.AuthService;
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

    private AuthService userService;
    private JwtService jwtService;

    /**
     * ユーザーを追加する
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, Object>> signup(
            @Validated @RequestBody SignUpForm signUpForm) {
        final User signedUpUser = userService.signup(signUpForm);
        // JWTトークン発行
        String jwt = jwtService.generateJwt(signedUpUser);
        return ResponseEntity.ok()
                .body(Map.ofEntries(
                        Map.entry("accessToken", jwt),
                        Map.entry("message", "新規登録しました。")));
    }

    /**
     * ログインする
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginForm loginForm) {
        final User loginUser = userService.login(loginForm);
        // JWTトークン発行
        String jwt = jwtService.generateJwt(loginUser);
        return ResponseEntity.ok()
                .body(Map.ofEntries(
                        Map.entry("accessToken", jwt),
                        Map.entry("message", "ログインしました。")));
    }

}
