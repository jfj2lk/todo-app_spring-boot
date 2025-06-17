package app.controller;

import java.util.List;
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

import app.form.todo.CreateTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.service.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/todos")
public class TodoController {
    private final TodoService todoService;

    /**
     * Todo取得。
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Todo>> get(
            @PathVariable("projectId") Long projectId,
            @PathVariable("id") Long todoId) {
        Todo todo = todoService.get(todoId, projectId);
        return ResponseEntity.ok().body(Map.of("data", todo));
    }

    /**
     * 全てのTodo取得。
     */
    @GetMapping
    public ResponseEntity<Map<String, List<Todo>>> getAll(
            @PathVariable("projectId") Long projectId) {
        List<Todo> todos = todoService.getAll(projectId);
        return ResponseEntity.ok().body(Map.of("data", todos));
    }

    /**
     * Todo作成。
     */
    @PostMapping
    public ResponseEntity<Map<String, Todo>> create(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Validated CreateTodoForm createTodoForm) {
        Todo createdTodo = todoService.create(projectId, createTodoForm);
        return ResponseEntity.ok().body(Map.of("data", createdTodo));
    }

    /**
     * Todo更新。
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Todo>> update(
            @PathVariable("projectId") Long projectId,
            @PathVariable("id") Long todoId,
            @RequestBody @Validated UpdateTodoForm UpdateTodoForm) {
        Todo updatedTodo = todoService.update(todoId, projectId, UpdateTodoForm);
        return ResponseEntity.ok().body(Map.of("data", updatedTodo));
    }

    /**
     * Todo削除。
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> delete(
            @PathVariable("projectId") Long projectId,
            @PathVariable("id") Long todoId) {
        Long deletedTodoId = todoService.delete(todoId, projectId);
        return ResponseEntity.ok().body(Map.of("data", deletedTodoId));
    }

    /**
     * Todoを完了状態にする。
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Map<String, Todo>> complete(
            @PathVariable("projectId") Long projectId,
            @PathVariable("id") Long todoId) {
        Todo completedTodo = todoService.complete(todoId, projectId);
        return ResponseEntity.ok().body(Map.of("data", completedTodo));
    }

    /**
     * Todoを未完了状態にする。
     */
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<Map<String, Todo>> incomplete(
            @PathVariable("projectId") Long projectId,
            @PathVariable("id") Long todoId) {
        Todo incompletedTodo = todoService.incomplete(todoId, projectId);
        return ResponseEntity.ok().body(Map.of("data", incompletedTodo));
    }
}
