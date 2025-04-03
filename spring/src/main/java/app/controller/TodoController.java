package app.controller;

import app.form.todo.add.AddTodoForm;
import app.form.todo.update.UpdateTodoForm;
import app.model.Todo;
import app.service.TodoService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 全てのTodo取得
    @GetMapping("/todos")
    public ResponseEntity<Map<String, Iterable<Todo>>> getAllTodos() {
        // DBから全てのTodoを取得し、その結果を返す
        Iterable<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok().body(Map.of("data", todos));
    }

    // Todo追加
    @PostMapping("/todos")
    public ResponseEntity<Map<String, Todo>> addTodo(@Validated @RequestBody AddTodoForm addTodoForm,
            BindingResult bindingResult) {
        // DBにフォームから送信されたTodoデータを保存し、その結果を返す
        Todo addedTodo = todoService.addTodo(addTodoForm);
        return ResponseEntity.ok().body(Map.of("data", addedTodo));

    }

    // Todo更新
    @PatchMapping("/todos/{id}")
    public ResponseEntity<Map<String, Todo>> updateTodo(@PathVariable("id") Long id,
            @Validated @RequestBody UpdateTodoForm updateTodoForm,
            BindingResult bindingResult) {
        // フォームから送られたTodoデータで更新し、その結果を返す
        Todo updatedTodo = todoService.updateTodo(id, updateTodoForm);
        return ResponseEntity.ok().body(Map.of("data", updatedTodo));
    }

    // Todo削除
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Map<String, Long>> deleteTodo(@PathVariable("id") Long id) {
        // パスパラメータのIDのTodoを削除
        Long deletedTodoId = todoService.deleteTodo(id);
        return ResponseEntity.ok(Map.of("data", deletedTodoId));
    }
}
