package app.seed;

import org.springframework.stereotype.Component;

import app.model.User;
import app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserSeeder {

    private final UserRepository userRepository;

    /**
     * ユーザーの初期データをシードする
     */
    public void seedInitialUser() {
        final User user1 = new User("a", "a@a", "a");
        final User user2 = new User("b", "b@b", "b");
        userRepository.save(user1);
        userRepository.save(user2);
    }

}
