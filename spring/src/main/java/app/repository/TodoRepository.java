package app.repository;

import org.springframework.stereotype.Repository;

import app.model.Todo;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
