package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.form.todo.AddTodoForm;
import app.model.Todo;
import app.service.TodoService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public Iterable<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping("/todos")
    public Object addTodo(@Validated @RequestBody AddTodoForm addTodoForm, BindingResult bindingResult) {
        // バリデーションエラーが発生した場合は、バリデーションエラーを返す
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // DBにフォームから送信されたTodoデータを保存し、その結果を返す
        Todo addedTodo = todoService.addTodo(addTodoForm);
        return addedTodo;
    }
}
