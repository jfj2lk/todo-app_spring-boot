package app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.exception.ModelNotFoundException;
import app.form.todo.CreateTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.ProjectRepository;
import app.repository.TodoRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ProjectRepository projectRepository;

    /**
     * Todo取得。
     */
    public Todo get(Long todoId, Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        return todoRepository
                .findByIdAndProjectId(todoId, projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
    }

    /**
     * 全てのTodo取得。
     */
    public List<Todo> getAll(Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        return todoRepository.findAllByProjectId(projectId);
    }

    /**
     * Todo作成。
     */
    public Todo create(Long projectId, CreateTodoForm createTodoForm) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        Todo createTodo = new Todo(createTodoForm, projectId);
        return todoRepository.save(createTodo);
    }

    /**
     * Todo更新。
     */
    public Todo update(Long todoId, Long projectId, UpdateTodoForm updateTodoForm) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        Todo updateTodo = todoRepository
                .findByIdAndProjectId(todoId, projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        updateTodo.updateWithForm(updateTodoForm);
        return todoRepository.save(updateTodo);
    }

    /**
     * Todo削除。
     */
    public Long delete(Long todoId, Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        Todo deleteTodo = todoRepository
                .findByIdAndProjectId(todoId, projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        todoRepository.delete(deleteTodo);
        return todoId;
    }

    /**
     * Todoを完了状態にする。
     */
    public Todo complete(Long todoId, Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        Todo completeTodo = todoRepository
                .findByIdAndProjectId(todoId, projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        completeTodo.setIsCompleted(true);
        return todoRepository.save(completeTodo);
    }

    /**
     * Todoを未完了状態にする。
     */
    public Todo incomplete(Long todoId, Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        Todo completeTodo = todoRepository
                .findByIdAndProjectId(todoId, projectId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        completeTodo.setIsCompleted(false);
        return todoRepository.save(completeTodo);
    }
}
