package app.seeder;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import app.model.User;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestUserSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * テスト用のユーザーの初期データをシードする
     */
    public void seedInitialUser() {
        final User user1 = new User("a", "a@a", passwordEncoder.encode("a"));
        final User user2 = new User("b", "b@b", passwordEncoder.encode("b"));
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
