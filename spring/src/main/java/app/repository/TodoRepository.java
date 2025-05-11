package app.repository;

import org.springframework.stereotype.Repository;

import app.model.Todo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    // 指定したユーザーIDに一致するTodoを取得する
    Iterable<Todo> findAllByUserId(Long userId);

    // 指定したIDとユーザーIDに一致するTodoを取得する
    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    // 指定したIDとユーザーIDに一致するTodoを削除する
    void deleteByIdAndUserId(Long id, Long userId);
}
