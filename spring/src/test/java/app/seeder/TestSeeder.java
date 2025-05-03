package app.seeder;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestSeeder {
    private final TestUserSeeder testUserSeeder;
    private final TestTodoSeeder testTodoSeeder;

    /**
     * テスト用の初期データをシードする
     */
    public void run() {
        testUserSeeder.seedInitialUser();
        testTodoSeeder.seedInitialTodo();
    }
}
