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
        Todo todo1 = new Todo("todo1", "desc");
        Todo todo2 = new Todo("todo2", "desc");
        todoRepository.save(todo1);
        todoRepository.save(todo2);
    }
}
