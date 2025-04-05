package app.service;

import app.form.todo.add.AddTodoForm;
import app.form.todo.add.AddTodoInput;
import app.form.todo.update.UpdateTodoForm;
import app.form.todo.update.UpdateTodoInput;
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
        return todoRepository.findAll();
    }

    /**
     * Todoを追加する
     */
    @Transactional
    public Todo addTodo(AddTodoForm addTodoForm) {
        // フォームからTodoの入力データを取得し、その値でTodoオブジェクト作成
        AddTodoInput todoAddInput = addTodoForm.getTodo();
        Todo addTodo = new Todo(todoAddInput.getName(), todoAddInput.getDesc());

        return todoRepository.save(addTodo);
    }

    /**
     * Todoを更新する
     */
    @Transactional
    public Todo updateTodo(Long id, UpdateTodoForm updateTodoForm) throws EntityNotFoundException {
        // フォームからTodoの入力データを取得し、その値でTodoオブジェクト作成
        Todo updateTodo = todoRepository.findById(id).get();
        UpdateTodoInput updateTodoInput = updateTodoForm.getTodo();
        updateTodo.updateWithForm(updateTodoInput);

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
