package app.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Seeder implements CommandLineRunner {
    private TodoSeeder todoSeeder;

    public Seeder(TodoSeeder todoSeeder) {
        this.todoSeeder = todoSeeder;
    }

    @Override
    public void run(String... args) throws Exception {
        todoSeeder.seedInitialTodo();
    }
}
