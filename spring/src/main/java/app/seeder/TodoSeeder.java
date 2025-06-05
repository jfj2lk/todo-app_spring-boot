package app.seeder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import app.model.Label;
import app.model.Todo;
import app.model.TodoLabel;
import app.repository.LabelRepository;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TodoSeeder {
        private final TodoRepository todoRepository;
        private final LabelRepository labelRepository;

        /**
         * Todoの初期データをシードする
         */
        public void seedInitialTodo() {
                LocalDate nowDate = LocalDate.now();
                LocalTime nowTime = LocalTime.now();

                // Labelの初期データ作成
                List<Label> labels = List.of(
                                new Label("家事"),
                                new Label("健康"));
                labelRepository.saveAll(labels);

                // Todoの初期データ作成
                List<Todo> todos = List.of(
                                new Todo(null, 1L, true, "todo1", "desc1", 1, nowDate, nowTime, null, null,
                                                Set.of(new TodoLabel(1L))),
                                new Todo(null, 1L, false, "todo2", "desc2", 2, nowDate, nowTime, null, null,
                                                Set.of(new TodoLabel(1L), new TodoLabel(2L))),
                                new Todo(null, 2L, true, "todo3", "desc3", 3, nowDate, nowTime, null, null,
                                                Set.of(new TodoLabel(1L))),
                                new Todo(null, 2L, false, "todo4", "desc4", 4, nowDate, nowTime, null, null,
                                                Set.of(new TodoLabel(1L), new TodoLabel(2L))));
                todoRepository.saveAll(todos);
        }
}
