package app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.dto.UserResponseDto;
import app.form.user.LoginForm;
import app.form.user.SignUpForm;
import app.form.user.UpdateUserForm;
import app.model.User;
import app.service.AuthService;
import app.utils.JwtUtils;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private JwtUtils jwtService;

    /**
     * 新規登録する。
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody @Validated SignUpForm signUpForm) {
        // 新規登録処理を行い、DBに追加されたUser情報を取得する
        final User signedUpUser = authService.signup(signUpForm);
        // JWTトークン発行
        final String jwt = jwtService.generateJwt(signedUpUser);
        // レスポンス用のユーザーDTOを作成
        final UserResponseDto userInfo = new UserResponseDto(signedUpUser.getId(), signedUpUser.getName(),
                signedUpUser.getEmail());

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("accessToken", jwt),
                Map.entry("message", "新規登録しました。"),
                Map.entry("userInfo", userInfo)));
    }

    /**
     * ログインする。
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Validated LoginForm loginForm) {
        // ログイン処理を行い、DBに保存されているUser情報を取得する
        final User loginUser = authService.login(loginForm);
        // JWTトークン発行
        final String jwt = jwtService.generateJwt(loginUser);
        // レスポンス用のユーザーDTOを作成
        final UserResponseDto userInfo = new UserResponseDto(loginUser.getId(), loginUser.getName(),
                loginUser.getEmail());

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("accessToken", jwt),
                Map.entry("message", "ログインしました。"),
                Map.entry("userInfo", userInfo)));
    }

    @PatchMapping("/auth/user")
    public ResponseEntity<Map<String, Object>> updateUser(
            @AuthenticationPrincipal String userId,
            @RequestBody @Validated UpdateUserForm form) {
        User updatedUser = authService.updateUser(Long.valueOf(userId), form);
        final UserResponseDto userInfo = new UserResponseDto(updatedUser.getId(), updatedUser.getName(),
                updatedUser.getEmail());
        return ResponseEntity.ok(Map.of("data", userInfo));
    }
}
