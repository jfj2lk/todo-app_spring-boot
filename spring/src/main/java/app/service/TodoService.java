package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.todo.AddTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;

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
        Todo addTodo = new Todo(addTodoForm.getName(), addTodoForm.getDesc());
        return todoRepository.save(addTodo);
    }

}
