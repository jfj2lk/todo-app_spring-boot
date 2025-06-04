package app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.model.TodoLabel;

public interface TodoLabelRepository extends CrudRepository<TodoLabel, Long> {
    List<TodoLabel> findAllByTodoId(Long todoId);
}
