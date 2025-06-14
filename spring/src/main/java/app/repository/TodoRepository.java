package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import app.model.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    /**
     * CrudRepositoryのfindAll()をオーバーライドして、戻り値をList型にする
     */
    @Override
    @NonNull
    List<Todo> findAll();

    /**
     * 指定したprojectIdに一致する全てのTodoを取得する
     */
    List<Todo> findAllByProjectId(Long projectId);

    /**
     * 指定したIdとprojectIdに一致するTodoを取得する
     */
    Optional<Todo> findByIdAndProjectId(Long id, Long projectId);
}
