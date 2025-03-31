package app.service;

import app.form.todo.AddTodoForm;
import app.form.todo.AddTodoInput;
import app.model.Todo;
import app.repository.TodoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

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
        AddTodoInput todoAddInput = addTodoForm.getTodo();
        Todo addTodo = new Todo(todoAddInput.getName(), todoAddInput.getDesc());
        return todoRepository.save(addTodo);
    }

}
