package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import app.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    /**
     * CrudRepositoryのfindAll()をオーバーライドして、戻り値をList型にする
     */
    @Override
    @NonNull
    List<Project> findAll();

    /**
     * 指定したUserIdに一致する全てのModelを取得する
     */
    List<Project> findAllByUserId(Long userId);

    /**
     * 指定したIdとUserIdに一致するModelを取得する
     */
    Optional<Project> findByIdAndUserId(Long id, Long userId);
}
