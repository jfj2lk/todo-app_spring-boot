package app.service;

import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Label;
import app.model.Todo;
import app.model.TodoLabel;
import app.repository.LabelRepository;
import app.repository.TodoLabelRepository;
import app.repository.TodoRepository;
import app.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final SecurityUtils securityUtils;
    private final LabelRepository labelRepository;
    private final TodoLabelRepository todoLabelRepository;

    /**
     * 全てのTodoをDBから取得する。
     */
    public Iterable<Todo> getAllTodos() {
        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // 全てのTodo取得
        return todoRepository.findAllByUserId(currentUserId);
    }

    /**
     * Todoを追加する。
     */
    public Todo addTodo(AddTodoForm addTodoForm) {
        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // フォームの値でTodoオブジェクトを作成する
        Todo addTodo = new Todo(addTodoForm, currentUserId);
        // Todo追加
        Todo addedTodo = todoRepository.save(addTodo);

        // フォームにlabelIdが含まれている場合は、中間テーブルに保存する
        if (addTodoForm.getLabelId() != null) {
            saveTodoLabelRelation(addedTodo.getId(), addTodoForm.getLabelId());
        }

        // 追加されたTodo情報を返す
        return addedTodo;
    }

    /**
     * 指定したTodoとLabelの関連を保存する
     */
    public void saveTodoLabelRelation(Long todoId, Long labelId) {
        // 指定されたIDのラベルが存在するか確認
        labelRepository.findById(labelId)
                .orElseThrow(() -> new RuntimeException("指定されたラベルが見つかりません。"));
        // 中間テーブルのレコードを作成
        TodoLabel todoLabel = new TodoLabel(todoId, labelId);
        todoLabelRepository.save(todoLabel);
    }

    /**
     * Todoを更新する。
     */
    public Todo updateTodo(Long todoId, UpdateTodoForm updateTodoForm)
            throws RuntimeException {
        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // 更新対象のTodoをDBから取得
        Todo updateTodo = todoRepository.findByIdAndUserId(todoId, currentUserId)
                .orElseThrow(() -> new RuntimeException("更新対象のTodoが見つかりませんでした。"));
        // フォームの値でTodoオブジェクトを更新する
        updateTodo.updateWithForm(updateTodoForm);
        // Todoを更新し、更新結果を返す
        return todoRepository.save(updateTodo);
    }

    /**
     * Todoを削除する。
     */
    public Long deleteTodo(Long todoId) throws RuntimeException {
        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // 削除対象のTodoをDBから取得
        Todo deleteTodo = todoRepository.findByIdAndUserId(todoId, currentUserId)
                .orElseThrow(() -> new RuntimeException("削除対象のTodoが見つかりませんでした。"));
        // Todo削除
        todoRepository.delete(deleteTodo);
        // 削除したTodoのIDを返す
        return todoId;
    }

    /**
     * Todo完了・未完了状態切り替え。
     */
    public Todo toggleCompletedTodo(Long todoId) {
        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // 更新対象のTodoをDBから取得
        Todo updateTodo = todoRepository.findByIdAndUserId(todoId, currentUserId)
                .orElseThrow(() -> new RuntimeException("更新対象のTodoが見つかりませんでした。"));
        // isCompletedの値を反転してセットする
        updateTodo.setIsCompleted(!updateTodo.getIsCompleted());
        // todoを更新し、更新後の値を返す
        return todoRepository.save(updateTodo);
    }
}
