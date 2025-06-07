package app.service;

import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Label;
import app.model.Todo;
import app.repository.LabelRepository;
import app.repository.TodoRepository;
import app.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final SecurityUtils securityUtils;
    private final LabelRepository labelRepository;

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
        // 指定されたLabelIdsに対応するレコードが全て存在しない場合は例外を投げる
        if (!isAllLabelsExist(addTodoForm.getLabelIds())) {
            throw new RuntimeException("指定されたラベルが見つかりません。");
        }

        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // フォームの値でTodoオブジェクトを作成する
        Todo addTodo = new Todo(addTodoForm, currentUserId);

        // Todoを追加し、追加後の値を返す
        return todoRepository.save(addTodo);
    }

    /**
     * Todoを更新する。
     */
    public Todo updateTodo(Long todoId, UpdateTodoForm updateTodoForm) {
        // 指定されたLabelIdsに対応するレコードが全て存在しない場合は例外を投げる
        if (!isAllLabelsExist(updateTodoForm.getLabelIds())) {
            throw new RuntimeException("指定されたラベルが見つかりません。");
        }

        // ログイン中のユーザーIDを取得
        Long currentUserId = securityUtils.getCurrentUserId();
        // 更新対象のTodoをDBから取得
        Todo updateTodo = todoRepository.findByIdAndUserId(todoId, currentUserId)
                .orElseThrow(() -> new RuntimeException("更新対象のTodoが見つかりませんでした。"));
        // フォームの値でTodoオブジェクトを更新する
        updateTodo.updateWithForm(updateTodoForm);

        // Todoを更新し、更新後の値を返す
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

    /*
     * 指定されたLabelがDBに全て存在するか確認する
     */
    public boolean isAllLabelsExist(Set<Long> labelIds) {
        // nullや中身が空の場合は、全てのLabelが存在すると判定する
        if (labelIds == null || labelIds.isEmpty()) {
            return true;
        }

        // IDが一致するレコードを全て取得する
        Iterable<Label> existingLabels = labelRepository.findAllById(labelIds);
        // IDが一致したレコードの数を取得する
        long existingLabelsCount = StreamSupport.stream(existingLabels.spliterator(), false).count();

        // 指定されたIDの数と実際に存在するレコードの数が一致しているか判定する
        return existingLabelsCount == labelIds.size();
    }
}
