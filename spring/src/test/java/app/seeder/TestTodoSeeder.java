package app.seeder;

import java.util.List;
import org.springframework.stereotype.Component;
import app.model.Todo;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Data
@AllArgsConstructor
public class TestTodoSeeder {
    private final TodoRepository todoRepository;
    private final List<Todo> seedTodos = List.of(
            new Todo("name1", "desc1"),
            new Todo("name2", "desc2"));

    /**
     * テスト用のTodoの初期データをシードする
     */
    public void seedInitialTodo() {
        todoRepository.saveAll(seedTodos);
    }
}
