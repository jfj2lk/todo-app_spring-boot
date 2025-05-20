package app.seeder;

import java.time.LocalDate;
import java.time.LocalTime;
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
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        List<Todo> saveTodos = List.of(
                new Todo(1L, true, "todo1", "desc", 1, nowDate, nowTime),
                new Todo(1L, false, "todo2", "desc", 2, nowDate, nowTime),
                new Todo(2L, true, "todo3", "desc", 3, nowDate, nowTime),
                new Todo(2L, false, "todo4", "desc", 4, nowDate, nowTime));
        todoRepository.saveAll(saveTodos);
    }
}
