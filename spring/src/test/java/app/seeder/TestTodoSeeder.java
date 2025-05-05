package app.seeder;

import java.util.List;
import org.springframework.stereotype.Component;
import app.model.Todo;
import app.repository.TodoRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class TestTodoSeeder {
    private final TodoRepository todoRepository;
    // テスト時に作成するTodoの情報
    private final List<Todo> seedTodos = List.of(
            new SeedTodoBuilder().build(1L, "name1", "desc1"),
            new SeedTodoBuilder().build(2L, "name2", "desc2"));

    /**
     * テスト用のTodoの初期データを作成する
     */
    public void seedInitialTodo() {
        // 元のオブジェクトの値が変更されないように、保存用のリストを作成する
        List<Todo> saveTodos = seedTodos
                .stream()
                .map(seedTodo -> new Todo(seedTodo.getName(), seedTodo.getDesc()))
                .toList();
        todoRepository.saveAll(saveTodos);
    }
}


/**
 * テスト用のTodoを作成するビルダークラス
 */
@Data
@NoArgsConstructor
class SeedTodoBuilder {
    /**
     * 指定したID、名前、説明を持つTodoオブジェクトを作成する
     */
    public Todo build(Long id, String name, String desc) {
        Todo todo = new Todo(name, desc);
        todo.setId(id);
        return todo;
    }
}
