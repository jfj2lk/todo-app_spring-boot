package app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
  // 指定したユーザーIDに一致するProjectを取得する
  Iterable<Project> findAllByUserId(Long userId);

  // 指定したIDとユーザーIDに一致するProjectを取得する
  Optional<Project> findByIdAndUserId(Long id, Long userId);

  // 指定したIDとユーザーIDに一致するProjectを削除する
  void deleteByIdAndUserId(Long id, Long userId);
}
