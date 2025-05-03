package app;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import app.form.todo.add.AddTodoForm;
import app.form.todo.add.AddTodoFieldForm;
import app.form.todo.update.UpdateTodoForm;
import app.form.todo.update.UpdateTodoFieldForm;
import app.model.Todo;
import app.repository.TodoRepository;
import app.service.TodoService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AllArgsConstructor
public class TodoServiceTest {
    private TodoService todoService;
    private TodoRepository todoRepository;
    private EntityManager entityManager;

    @Test
    void 全てのTodoが取得できるか() {
        // 全てのTodoの数
        final int dbAllTodosCount = 2;

        // 全てのTodo取得
        final Iterable<Todo> allTodos = todoService.getAllTodos();
        // 取得したTodoの数を取得
        final long allTodosCount = StreamSupport.stream(allTodos.spliterator(), false).count();

        // テスト
        assertEquals(dbAllTodosCount, allTodosCount, "全てのTodoの数取得できていることを確認");
    }

    @Test
    void Todoが追加できるか() {
        // Todo入力内容の作成
        final AddTodoFieldForm addTodoInput = new AddTodoFieldForm("AddTodo", "AddDesc");
        final AddTodoForm addTodoForm = new AddTodoForm(addTodoInput);

        // Todo追加
        final Todo addedTodo = todoService.addTodo(addTodoForm);

        // テスト
        assertAll("指定のプロパティが存在する、または指定の値が入っていることを確認",
                () -> assertNotNull(addedTodo.getId()),
                () -> assertEquals("AddTodo", addedTodo.getName()),
                () -> assertEquals("AddDesc", addedTodo.getDesc()),
                () -> assertNotNull(addedTodo.getCreatedAt()),
                () -> assertNotNull(addedTodo.getUpdatedAt()));
    }

    @Test
    void Todoが更新できるか() throws InterruptedException {
        // 更新対象のTodoのID
        final Long updateTodoId = 1l;
        // 更新前のTodo取得
        final Todo beforeUpdateTodo = todoRepository.findById(updateTodoId).get();
        entityManager.clear();

        // Todo入力内容の作成
        final UpdateTodoFieldForm updateTodoInput =
                new UpdateTodoFieldForm("UpdateTodo", "UpdateDesc");
        final UpdateTodoForm updateTodoForm = new UpdateTodoForm(updateTodoInput);

        // Todo更新
        Thread.sleep(1000);
        final Todo updatedTodo = todoService.updateTodo(updateTodoId, updateTodoForm);

        // テスト
        assertAll("値が更新されていないことを確認",
                () -> assertEquals(beforeUpdateTodo.getCreatedAt(), updatedTodo.getCreatedAt()),
                () -> assertEquals(beforeUpdateTodo.getId(), updatedTodo.getId()));
        assertAll("値が更新されていることを確認",
                () -> assertEquals("UpdateTodo", updatedTodo.getName()),
                () -> assertEquals("UpdateDesc", updatedTodo.getDesc()),
                () -> assertNotEquals(beforeUpdateTodo.getUpdatedAt(), updatedTodo.getUpdatedAt()));
    }

    @Test
    void Todoが削除できるか() {
        // 全てのTodoの数
        final int dbAllTodosCount = 2;
        // 削除対象のTodoのID
        final Long deleteTodoId = 1l;

        // Todo削除
        final Long deletedTodoId = todoService.deleteTodo(deleteTodoId);
        // 削除したTodoを取得
        final Optional<Todo> deletedTodo = todoRepository.findById(deleteTodoId);

        // 全てのTodo取得
        final Iterable<Todo> allTodos = todoService.getAllTodos();
        // 取得したTodoの数を取得
        final long allTodosCount = StreamSupport.stream(allTodos.spliterator(), false).count();

        // テスト
        assertEquals(dbAllTodosCount - 1, allTodosCount, "Todoの件数が1件少なくなっていることを確認");
        assertEquals(deleteTodoId, deletedTodoId, "削除したTodoのIDが取得できていることを確認");
        assertFalse(deletedTodo.isPresent(), "削除したTodoが存在しないことを確認");
    }
}
