package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import app.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * CrudRepositoryのfindAll()をオーバーライドして、戻り値をList型にする
     */
    @Override
    @NonNull
    List<User> findAll();

    /**
     * 指定したEmailに一致するUserを取得する
     */
    Optional<User> findByEmail(String email);

    /**
     * 指定したEmailとPasswordに一致するUserを取得する
     */
    Optional<User> findByEmailAndPassword(String email, String passwrod);
}
