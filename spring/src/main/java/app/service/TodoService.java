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

import java.util.List;
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
        Set<Long> labelIds = addTodoForm.getLabelIds();
        if (labelIds != null && !labelIds.isEmpty()) {
            saveTodoLabelRelation(addedTodo.getId(), labelIds);
        }

        // 追加されたTodo情報を返す
        return addedTodo;
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
        // Todo更新
        Todo updatedTodo = todoRepository.save(updateTodo);

        // フォームにlabelIdが含まれている場合は、中間テーブルに保存する
        Set<Long> labelIds = updateTodoForm.getLabelIds();
        if (labelIds != null && !labelIds.isEmpty()) {
            saveTodoLabelRelation(updateTodo.getId(), updateTodoForm.getLabelIds());
        }

        return updatedTodo;
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

    /**
     * 指定したTodoとLabelの関連を保存する
     */
    public void saveTodoLabelRelation(Long todoId, Set<Long> newLabelIds) {
        // 指定されたLabelIdsの内、実際にDBに存在するLabelのIDを取得
        List<Long> existingLabels = StreamSupport
                .stream(labelRepository.findAllById(newLabelIds).spliterator(), false)
                .map(Label::getId)
                .toList();
        // 指定されたLabelIdsのLabelが全て存在しなかった場合
        if (!existingLabels.containsAll(newLabelIds)) {
            List<Long> notFoundIds = newLabelIds.stream()
                    .filter(newLabelId -> !existingLabels.contains(newLabelId))
                    .toList();
            throw new RuntimeException("指定されたラベルが見つかりません。" + notFoundIds);
        }

        // 指定されたTodoIdに紐づく中間レコードのIDを全て取得
        List<TodoLabel> todoLabelsForTodoId = todoLabelRepository.findAllByTodoId(todoId);

        // 指定されたTodoIdに紐づく中間レコードの全てのLabelのIDを取得
        List<Long> currentLabelIds = todoLabelsForTodoId.stream()
                .map(TodoLabel::getLabelId).toList();

        // 新たに追加する中間レコード
        List<TodoLabel> todoLabelsForAdd = newLabelIds.stream()
                // 新しいラベルの内、現在のラベルに含まれていないものを抽出する
                .filter(newLabelId -> !currentLabelIds.contains(newLabelId))
                .map(addLabelId -> new TodoLabel(todoId, addLabelId))
                .toList();

        // 削除する中間レコードのIDのリストを作成
        List<TodoLabel> todoLabelsForDelete = todoLabelsForTodoId.stream()
                // 現在のラベルの内、新しいラベルに含まれていないものを抽出する
                .filter(todoLabelForTodoId -> !newLabelIds.contains(todoLabelForTodoId.getLabelId()))
                .toList();

        // 保存と削除を実行
        if (!todoLabelsForAdd.isEmpty()) {
            todoLabelRepository.saveAll(todoLabelsForAdd);
        }
        if (!todoLabelsForDelete.isEmpty()) {
            todoLabelRepository.deleteAll(todoLabelsForDelete);
        }
    }
}
