package app.seeder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import app.model.Todo;
import app.model.TodoLabel;
import app.repository.TodoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class TestTodoSeeder {
    private final TodoRepository todoRepository;
    private List<Todo> todos;

    /**
     * テスト用のTodoの初期データを作成する
     */
    public void seedInitialTodo() {
        // 現在日付と現在時刻を取得
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // Todoの初期データ作成
        todos = List.of(
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
