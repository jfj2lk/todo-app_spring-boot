package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.form.user.add.AddUserForm;
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

    /**
     * ユーザーを追加する
     */
    @PostMapping("/users")
    public Object signup(@Validated @RequestBody AddUserForm addUserForm) {
        final User addedUser = userService.addUser(addUserForm);
        return ResponseEntity.ok().body(Map.of("data", addedUser));
    }

}
