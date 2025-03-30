package app.seed;

import org.springframework.stereotype.Component;

import app.model.Todo;
import app.repository.TodoRepository;

@Component
public class TodoSeeder {

    private TodoRepository todoRepository;

    public TodoSeeder(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void seedInitialTodo() {
        Todo todo1 = new Todo(null, "todo1", null, null);
        Todo todo2 = new Todo(null, "todo2", null, null);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
    }
}
