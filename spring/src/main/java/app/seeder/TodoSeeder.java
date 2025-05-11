package app.seeder;

import java.util.List;

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
        List<Todo> saveTodos = List.of(
                new Todo(1L, "todo1", "desc"),
                new Todo(1L, true, "todo2", "desc"),
                new Todo(2L, "todo2", "desc"));
        todoRepository.saveAll(saveTodos);
    }
}
