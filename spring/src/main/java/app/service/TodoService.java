package app.service;

import app.form.todo.AddTodoForm;
import app.form.todo.update.UpdateTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

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
        // 全てのTodo取得
        return todoRepository.findAll();
    }

    /**
     * Todoを追加する
     */
    @Transactional
    public Todo addTodo(AddTodoForm addTodoForm) {
        // フォームの値でTodoオブジェクトを作成する
        Todo addTodo = new Todo(addTodoForm);
        // Todoを追加し、追加結果を返す
        return todoRepository.save(addTodo);
    }

    /**
     * Todoを更新する
     */
    @Transactional
    public Todo updateTodo(Long id, UpdateTodoForm updateTodoForm) throws EntityNotFoundException {
        // 更新対象のTodoをDBから取得
        Todo updateTodo = todoRepository.findById(id).get();
        // フォームの値でTodoオブジェクトを更新する
        updateTodo.updateWithForm(updateTodoForm);
        // Todoを更新し、更新結果を返す
        return todoRepository.save(updateTodo);
    }

    /**
     * Todoを削除する
     */
    public Long deleteTodo(Long id) throws EntityNotFoundException {
        // Todo削除
        todoRepository.deleteById(id);
        // 削除したTodoのIDを返す
        return id;
    }
}
