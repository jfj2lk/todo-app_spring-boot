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
                new Todo(1L, true, "todo1", "desc", 1),
                new Todo(1L, false, "todo2", "desc", 2),
                new Todo(2L, true, "todo3", "desc", 3),
                new Todo(2L, false, "todo4", "desc", 4));
        todoRepository.saveAll(saveTodos);
    }
}
