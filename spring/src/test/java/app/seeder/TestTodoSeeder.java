package app.seeder;

import org.springframework.stereotype.Component;
import app.model.Todo;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TestTodoSeeder {
    private final TodoRepository todoRepository;

    /**
     * テスト用のTodoの初期データをシードする
     */
    public void seedInitialTodo() {
        final Todo todo1 = new Todo("todo1", "desc");
        final Todo todo2 = new Todo("todo2", "desc");
        todoRepository.save(todo1);
        todoRepository.save(todo2);
    }
}
