package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.auth.JwtService;
import app.form.user.add.AddUserForm;
import app.form.user.login.LoginForm;
import app.model.User;
import app.service.UserService;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    /**
     * ユーザーを追加する
     */
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> signup(@Validated @RequestBody AddUserForm addUserForm) {
        final User addedUser = userService.addUser(addUserForm);
        // JWTトークン発行
        String jwtToken = jwtService.generateToken(addedUser);
        return ResponseEntity.ok().body(Map.ofEntries(Map.entry("data", addedUser), Map.entry("jwtToken", jwtToken)));
    }

    /**
     * ログインする
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginForm loginForm) {
        final User loginUser = userService.login(loginForm);
        // JWTトークン発行
        String jwtToken = jwtService.generateToken(loginUser);
        return ResponseEntity.ok().body(Map.ofEntries(Map.entry("data", loginUser), Map.entry("jwtToken", jwtToken)));
    }

}
