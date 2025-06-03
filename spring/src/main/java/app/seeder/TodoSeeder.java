package app.seeder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import app.model.Label;
import app.model.Todo;
import app.model.TodoLabel;
import app.repository.LabelRepository;
import app.repository.TodoLabelRepository;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TodoSeeder {
    private final TodoRepository todoRepository;
    private final LabelRepository labelRepository;
    private final TodoLabelRepository todoLabelRepository;

    /**
     * Todoの初期データをシードする
     */
    public void seedInitialTodo() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        List<Todo> todos = List.of(
                new Todo(1L, true, "todo1", "desc", 1, nowDate, nowTime),
                new Todo(1L, false, "todo2", "desc", 2, nowDate, nowTime),
                new Todo(2L, true, "todo3", "desc", 3, nowDate, nowTime),
                new Todo(2L, false, "todo4", "desc", 4, nowDate, nowTime));
        todoRepository.saveAll(todos);

        List<Label> labels = List.of(
                new Label("家事"),
                new Label("健康"));
        labelRepository.saveAll(labels);

        List<TodoLabel> todoLabels = List.of(
                new TodoLabel(1L, 1L),
                new TodoLabel(2L, 1L),
                new TodoLabel(1L, 2L));
        todoLabelRepository.saveAll(todoLabels);
    }
}
