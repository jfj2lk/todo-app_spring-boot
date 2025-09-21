package app.seeder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import app.model.Label;
import app.model.Project;
import app.model.Todo;
import app.model.TodoLabel;
import app.model.User;
import app.repository.LabelRepository;
import app.repository.ProjectRepository;
import app.repository.TodoRepository;
import app.repository.UserRepository;
import app.utils.PasswordUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Data
public class Seeder {
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;
    private final TodoRepository todoRepository;
    private final PasswordUtils passwordUtils;

    private List<User> users;
    private List<Label> labels;
    private List<Project> projects;
    private List<Todo> todos;

    public void seedInitialData() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // User
        users = List.of(
                new User(null, "a", "a@a", passwordUtils.encode("a"), null, null),
                new User(null, "b", "b@b", passwordUtils.encode("b"), null, null));
        userRepository.saveAll(users);

        // Label
        labels = List.of(
                new Label(null, 1L, "家事", null, null),
                new Label(null, 1L, "健康", null, null));
        labelRepository.saveAll(labels);

        // Project
        projects = List.of(
                new Project(null, 1L, "プライベート", null, null),
                new Project(null, 1L, "仕事", null, null));
        projectRepository.saveAll(projects);

        // Todo
        todos = List.of(
                new Todo(null, 1L, 1L, true, "todo1", "desc1", 1, nowDate, nowTime, null, null,
                        Set.of(new TodoLabel(1L))),
                new Todo(null, 1L, 1L, false, "todo2", "desc2", 2, nowDate, nowTime, null, null,
                        Set.of(new TodoLabel(1L), new TodoLabel(2L))),
                new Todo(null, 2L, 1L, true, "todo3", "desc3", 3, nowDate, nowTime, null, null,
                        Set.of(new TodoLabel(1L))),
                new Todo(null, 2L, 1L, false, "todo4", "desc4", 4, nowDate, nowTime, null, null,
                        Set.of(new TodoLabel(1L), new TodoLabel(2L))));
        todoRepository.saveAll(todos);
    }
}
