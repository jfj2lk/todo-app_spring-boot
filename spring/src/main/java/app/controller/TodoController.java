package app.controller;

import app.form.todo.add.AddTodoForm;
import app.model.Todo;
import app.service.TodoService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Map<String, Object>> addTodo(@Validated @RequestBody AddTodoForm addTodoForm,
            BindingResult bindingResult) {
        // バリデーションエラーが発生した場合は、バリデーションエラーを返す
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("data", bindingResult.getAllErrors()));
        }

        // DBにフォームから送信されたTodoデータを保存し、その結果を返す
        Todo addedTodo = todoService.addTodo(addTodoForm);
        return ResponseEntity.ok().body(Map.of("data", addedTodo));
    }
}
