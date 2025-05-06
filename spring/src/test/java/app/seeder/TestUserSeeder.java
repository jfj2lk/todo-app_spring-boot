package app.seeder;

import java.util.List;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import app.model.User;
import app.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class TestUserSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // テスト時に作成するUserの情報
    private final List<User> seedUsers = List.of(
            new User("a", "a@a", "a"),
            new User("b", "b@b", "b"));

    /**
     * テスト用のユーザーの初期データをシードする
     */
    public void seedInitialUser() {
        // 元のオブジェクトの情報が変更されないように、保存用のUser情報を作成
        List<User> saveSeedUsers = seedUsers.stream()
                .map(seedUser -> new User(seedUser.getName(), seedUser.getEmail(),
                        passwordEncoder.encode(seedUser.getPassword())))
                .toList();
        userRepository.saveAll(saveSeedUsers);
    }
}
