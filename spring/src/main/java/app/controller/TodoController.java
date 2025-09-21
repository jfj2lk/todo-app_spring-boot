package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.form.todo.CreateTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.service.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    /**
     * Todo取得。
     */
    @GetMapping("/todos/{id}")
    public ResponseEntity<Map<String, Todo>> get(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long todoId) {
        Todo todo = todoService.get(Long.valueOf(userId), todoId);
        return ResponseEntity.ok().body(Map.of("data", todo));
    }

    /**
     * 全てのTodo取得。
     * projectIdが指定されている場合は、指定のProjectに紐づく全てのTodo取得。
     * labelIdが指定されている場合は、指定のLabelに紐づく全てのTodo取得。
     * projectIdとlabelIdがどちらも指定されていない場合は、Userに紐づく全てのTodo取得。
     */
    @GetMapping("/todos")
    public ResponseEntity<Map<String, List<Todo>>> getAll(
            @AuthenticationPrincipal String userId,
            @RequestParam(name = "projectId", required = false) Long projectId,
            @RequestParam(name = "labelId", required = false) Long labelId) {
        List<Todo> todos = new ArrayList<>();

        if (projectId != null) {
            // projectIdが指定されている場合
            todos = todoService.getAllByProject(Long.valueOf(userId), projectId);
        } else if (labelId != null) {
            // labelIdが指定されている場合
            todos = todoService.getAllByLabel(Long.valueOf(userId), labelId);
        } else {
            // projectIdとlabelIdが指定されていない場合
            todos = todoService.getAll(Long.valueOf(userId));
        }

        return ResponseEntity.ok().body(Map.of("data", todos));
    }

    /**
     * Todo作成。
     * projectIdが指定されている場合は、指定のProjectに紐づくTodo作成。
     */
    @PostMapping("/todos")
    public ResponseEntity<Map<String, Todo>> create(
            @AuthenticationPrincipal String userId,
            @RequestParam(name = "projectId", required = false) Long projectId,
            @RequestBody @Validated CreateTodoForm createTodoForm) {
        Todo createdTodo = null;

        if (projectId != null) {
            // projectIdが指定されている場合
            createdTodo = todoService.createWithProject(createTodoForm, Long.valueOf(userId), projectId);
        } else {
            // projectIdが指定されていない場合
            createdTodo = todoService.create(createTodoForm, Long.valueOf(userId));
        }

        return ResponseEntity.ok().body(Map.of("data", createdTodo));
    }

    /**
     * Todo更新。
     */
    @PatchMapping("/todos/{id}")
    public ResponseEntity<Map<String, Todo>> update(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long todoId,
            @RequestBody @Validated UpdateTodoForm UpdateTodoForm) {
        Todo updatedTodo = todoService.update(UpdateTodoForm, Long.valueOf(userId), todoId);
        return ResponseEntity.ok().body(Map.of("data", updatedTodo));
    }

    /**
     * Todo削除。
     */
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Map<String, Long>> delete(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long todoId) {
        Long deletedTodoId = todoService.delete(Long.valueOf(userId), todoId);
        return ResponseEntity.ok().body(Map.of("data", deletedTodoId));
    }

    /**
     * Todoを完了状態にする。
     */
    @PatchMapping("/todos/{id}/complete")
    public ResponseEntity<Map<String, Todo>> complete(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long todoId) {
        Todo completedTodo = todoService.complete(Long.valueOf(userId), todoId);
        return ResponseEntity.ok().body(Map.of("data", completedTodo));
    }

    /**
     * Todoを未完了状態にする。
     */
    @PatchMapping("/todos/{id}/incomplete")
    public ResponseEntity<Map<String, Todo>> incomplete(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long todoId) {
        Todo incompleteTodo = todoService.incomplete(Long.valueOf(userId), todoId);
        return ResponseEntity.ok().body(Map.of("data", incompleteTodo));
    }
}
