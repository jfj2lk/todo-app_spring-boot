package app.seeder;

import java.util.List;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.model.User;
import app.repository.UserRepository;
import app.utils.PasswordUtils;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserSeeder {
    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    /**
     * ユーザーの初期データをシードする
     */
    public void seedInitialUser() {
        List<User> saveUsers = List.of(
                new User("a", "a@a", passwordUtils.encode("a")),
                new User("b", "b@b", passwordUtils.encode("b")));
        userRepository.saveAll(saveUsers);
    }

}
