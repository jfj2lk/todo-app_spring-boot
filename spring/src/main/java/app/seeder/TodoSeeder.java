package app.seeder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import app.model.Label;
import app.model.Project;
import app.model.Todo;
import app.model.TodoLabel;
import app.repository.LabelRepository;
import app.repository.ProjectRepository;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TodoSeeder {
    private final TodoRepository todoRepository;
    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;

    /**
     * Todoの初期データをシードする
     */
    public void seedInitialTodo() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // Projectの初期データ作成
        List<Project> lists = List.of(
                new Project(null, 1L, "プライベート", null, null),
                new Project(null, 1L, "仕事", null, null));
        projectRepository.saveAll(lists);

        // Labelの初期データ作成
        List<Label> labels = List.of(
                new Label(1L, "家事"),
                new Label(1L, "健康"));
        labelRepository.saveAll(labels);

        // Todoの初期データ作成
        List<Todo> todos = List.of(
                new Todo(1L, 1L, true, "todo1", "desc1", 1, nowDate, nowTime,
                        Set.of(new TodoLabel(1L))),
                new Todo(1L, 2L, false, "todo2", "desc2", 2, nowDate, nowTime,
                        Set.of(new TodoLabel(1L), new TodoLabel(2L))),
                new Todo(2L, 1L, true, "todo3", "desc3", 3, nowDate, nowTime,
                        Set.of(new TodoLabel(1L))),
                new Todo(2L, 2L, false, "todo4", "desc4", 4, nowDate, nowTime,
                        Set.of(new TodoLabel(1L), new TodoLabel(2L))));
        todoRepository.saveAll(todos);
    }
}
