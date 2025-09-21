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
     * 指定したidとuserIdに一致するTodoを取得する
     */
    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    /**
     * 指定したuserIdに一致する全てのTodoを取得する
     */
    List<Todo> findAllByUserId(Long userId);

    /**
     * 指定したuserIdとprojectIdに一致する全てのTodoを取得する
     */
    List<Todo> findAllByUserIdAndProjectId(Long userId, Long projectId);

    /**
     * 指定したuserIdとlabelIdに一致する全てのTodoを取得する
     */
    @Query("""
            SELECT t.*
            FROM todos t
            JOIN todo_label tl ON t.id = tl.todo_id
            WHERE tl.label_id = :labelId AND t.user_id = :userId
            """)
    List<Todo> findAllByUserIdAndLabelId(Long userId, Long labelId);
}
