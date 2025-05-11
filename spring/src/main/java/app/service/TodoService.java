package app.service;

import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepository todoRepository;

    /**
     * 全てのTodoをDBから取得する
     */
    @Transactional
    public Iterable<Todo> getAllTodos() {
        // ログイン中のユーザーIDを取得
        Long loginUserId = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // 全てのTodo取得
        return todoRepository.findAllByUserId(loginUserId);
    }

    /**
     * Todoを追加する
     */
    @Transactional
    public Todo addTodo(AddTodoForm addTodoForm) {
        // ログイン中のユーザーIDを取得
        Long loginUserId = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // フォームの値でTodoオブジェクトを作成する
        Todo addTodo = new Todo(addTodoForm, loginUserId);
        // Todoを追加し、追加結果を返す
        return todoRepository.save(addTodo);
    }

    /**
     * Todoを更新する
     */
    @Transactional
    public Todo updateTodo(Long id, UpdateTodoForm updateTodoForm)
            throws RuntimeException {
        // ログイン中のユーザーIDを取得
        Long loginUserId = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // 更新対象のTodoをDBから取得
        Todo updateTodo = todoRepository.findByIdAndUserId(id, loginUserId)
                .orElseThrow(() -> new RuntimeException("更新対象のTodoが見つかりませんでした。"));
        // フォームの値でTodoオブジェクトを更新する
        updateTodo.updateWithForm(updateTodoForm);
        // Todoを更新し、更新結果を返す
        return todoRepository.save(updateTodo);
    }

    /**
     * Todoを削除する
     */
    public Long deleteTodo(Long id) throws RuntimeException {
        // ログイン中のユーザーIDを取得
        Long loginUserId = Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Todo deleteTodo = todoRepository.findByIdAndUserId(id, loginUserId)
                .orElseThrow(() -> new RuntimeException("削除対象のTodoが見つかりませんでした。"));
        // Todo削除
        todoRepository.delete(deleteTodo);
        // 削除したTodoのIDを返す
        return id;
    }
}
