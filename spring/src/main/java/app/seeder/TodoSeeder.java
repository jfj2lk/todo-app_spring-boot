package app.seeder;

import org.springframework.stereotype.Component;

import app.model.Todo;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TodoSeeder {

    private final TodoRepository todoRepository;

    /**
     * Todoの初期データをシードする
     */
    public void seedInitialTodo() {
        final Todo todo1 = new Todo(1L, "todo1", "desc");
        final Todo todo2 = new Todo(2L, "todo2", "desc");
        todoRepository.save(todo1);
        todoRepository.save(todo2);
    }
}
