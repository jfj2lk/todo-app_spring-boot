package app.controller;

import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.service.TodoService;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TodoController {
    private final TodoService todoService;

    // 全てのTodo取得
    @GetMapping("/todos")
    public ResponseEntity<Map<String, Iterable<Todo>>> getAllTodos() {
        // DBから全てのTodoを取得し、その結果を返す
        Iterable<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok().body(Map.of("data", todos));
    }

    // Todo追加
    @PostMapping("/todos")
    public ResponseEntity<Map<String, Todo>> addTodo(
            @RequestBody @Validated AddTodoForm addTodoForm) {
        // DBにフォームから送信されたTodoデータを保存し、その結果を返す
        Todo addedTodo = todoService.addTodo(addTodoForm);
        return ResponseEntity.ok().body(Map.of("data", addedTodo));

    }

    // Todo更新
    @PatchMapping("/todos/{id}")
    public ResponseEntity<Map<String, Todo>> updateTodo(
            @PathVariable("id") Long id,
            @RequestBody @Validated UpdateTodoForm updateTodoForm) {
        // フォームから送られたTodoデータで更新し、その結果を返す
        Todo updatedTodo = todoService.updateTodo(id, updateTodoForm);
        return ResponseEntity.ok().body(Map.of("data", updatedTodo));
    }

    // Todo削除
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Map<String, Long>> deleteTodo(
            @PathVariable("id") Long id) {
        // パスパラメータのIDのTodoを削除
        Long deletedTodoId = todoService.deleteTodo(id);
        return ResponseEntity.ok(Map.of("data", deletedTodoId));
    }

    // Todo完了・未完了状態切り替え
    @PatchMapping("/todos/{id}/toggleComplete")
    public ResponseEntity<Map<String, Object>> toggleCompleteTodo(
            @PathVariable("id") Long id) {
        Todo updateTodo = todoService.toggleCompleteTodo(id);
        return ResponseEntity.ok(Map.of("data", updateTodo));
    }
}
