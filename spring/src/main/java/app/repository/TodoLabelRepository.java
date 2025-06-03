package app.repository;

import org.springframework.data.repository.CrudRepository;

import app.model.TodoLabel;

public interface TodoLabelRepository extends CrudRepository<TodoLabel, Long> {

}
