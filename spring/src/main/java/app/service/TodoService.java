package app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.exception.ModelNotFoundException;
import app.form.todo.CreateTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.LabelRepository;
import app.repository.ProjectRepository;
import app.repository.TodoRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ProjectRepository projectRepository;
    private final LabelRepository labelRepository;

    /**
     * Todo取得。
     */
    public Todo get(Long userId, Long todoId) {
        return todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
    }

    /**
     * 全てのTodo取得。
     */
    public List<Todo> getAll(Long userId) {
        return todoRepository.findAllByUserId(userId);
    }

    /**
     * 特定のProjectに紐づく全てのTodo取得。
     */
    public List<Todo> getAllByProject(Long userId, Long projectId) {
        projectRepository
                .findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        return todoRepository.findAllByUserIdAndProjectId(userId, projectId);
    }

    /**
     * 特定のLabelに紐づく全てのTodo取得。
     */
    public List<Todo> getAllByLabel(Long userId, Long labelId) {
        labelRepository
                .findByIdAndUserId(labelId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたLabelが見つかりません。"));
        return todoRepository.findAllByUserIdAndLabelId(userId, labelId);
    }

    /**
     * Todo作成。
     */
    public Todo create(CreateTodoForm createTodoForm, Long userId) {
        Todo createTodo = new Todo(createTodoForm, userId);
        return todoRepository.save(createTodo);
    }

    /**
     * Projectに紐づくTodo作成。
     */
    public Todo createWithProject(CreateTodoForm createTodoForm, Long userId, Long projectId) {
        projectRepository
                .findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        Todo createTodo = new Todo(createTodoForm, userId, projectId);
        return todoRepository.save(createTodo);
    }

    /**
     * Todo更新。
     */
    public Todo update(UpdateTodoForm updateTodoForm, Long userId, Long todoId) {
        Todo updateTodo = todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        updateTodo.updateWithForm(updateTodoForm);
        return todoRepository.save(updateTodo);
    }

    /**
     * Todo削除。
     */
    public Long delete(Long userId, Long todoId) {
        Todo deleteTodo = todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        todoRepository.delete(deleteTodo);
        return todoId;
    }

    /**
     * Todoを完了状態にする。
     */
    public Todo complete(Long userId, Long todoId) {
        Todo completeTodo = todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        completeTodo.setIsCompleted(true);
        return todoRepository.save(completeTodo);
    }

    /**
     * Todoを未完了状態にする。
     */
    public Todo incomplete(Long userId, Long todoId) {
        Todo completeTodo = todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたTodoが見つかりません。"));
        completeTodo.setIsCompleted(false);
        return todoRepository.save(completeTodo);
    }
}
