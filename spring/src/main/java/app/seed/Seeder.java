package app.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    private final TodoSeeder todoSeeder;
    private final UserSeeder userSeeder;

    @Override
    public void run(String... args) throws Exception {
        userSeeder.seedInitialUser();
        todoSeeder.seedInitialTodo();
    }
}
