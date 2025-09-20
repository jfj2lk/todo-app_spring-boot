package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
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
     * 指定したuserIdに一致する全てのTodoを取得する
     */
    List<Todo> findAllByUserId(Long userId);

    /**
     * 指定したprojectIdに一致する全てのTodoを取得する
     */
    List<Todo> findAllByProjectId(Long projectId);

    /**
     * 指定したidとuserIdに一致するTodoを取得する
     */
    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    /**
     * 指定したidとprojectIdに一致するTodoを取得する
     */
    Optional<Todo> findByIdAndProjectId(Long id, Long projectId);

    /**
     * 指定したlabelIdに一致する全てのTodoを取得する
     */
    @Query("""
            SELECT t.*
            FROM todos t
            JOIN todo_label tl ON t.id = tl.todo_id
            WHERE tl.label_id = :labelId
            """)
    List<Todo> findAllByLabelId(Long labelId);
}
