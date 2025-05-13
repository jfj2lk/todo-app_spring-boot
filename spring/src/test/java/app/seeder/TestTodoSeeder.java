package app.seeder;

import java.util.List;
import org.springframework.stereotype.Component;
import app.model.Todo;
import app.repository.TodoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class TestTodoSeeder {
    private final TodoRepository todoRepository;
    // テスト時に作成するTodoの情報
    private final List<Todo> seedTodos = List.of(
            new Todo(1L, 1L, true, "name1", "desc1", null, null),
            new Todo(2L, 1L, false, "name2", "desc2", null, null),
            new Todo(3L, 2L, true, "name3", "desc3", null, null),
            new Todo(4L, 2L, false, "name4", "desc4", null, null));

    /**
     * テスト用のTodoの初期データを作成する
     */
    public void seedInitialTodo() {
        // 元のオブジェクトの値が変更されないように、保存用のリストを作成する
        List<Todo> saveTodos = seedTodos
                .stream()
                .map(seedTodo -> new Todo(seedTodo.getUserId(), seedTodo.getIsCompleted(), seedTodo.getName(),
                        seedTodo.getDesc()))
                .toList();
        todoRepository.saveAll(saveTodos);
    }
}
