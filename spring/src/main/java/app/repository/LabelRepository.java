package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import app.model.Label;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {
    /**
     * CrudRepositoryのfindAll()をオーバーライドして、戻り値をList型にする
     */
    @Override
    @NonNull
    List<Label> findAll();

    /**
     * 指定したuserIdに一致する全てのLabelを取得する
     */
    List<Label> findAllByUserId(Long userId);

    /**
     * 指定したIdとuserIdに一致するLabelを取得する
     */
    Optional<Label> findByIdAndUserId(Long id, Long userId);
}
