package app.repository;

import org.springframework.stereotype.Repository;

import app.model.Label;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {
  // 指定したユーザーIDに一致するLabelを取得する
  Iterable<Label> findAllByUserId(Long userId);

  // 指定したIDとユーザーIDに一致するLabelを取得する
  Optional<Label> findByIdAndUserId(Long id, Long userId);

  // 指定したIDとユーザーIDに一致するLabelを削除する
  void deleteByIdAndUserId(Long id, Long userId);
}
