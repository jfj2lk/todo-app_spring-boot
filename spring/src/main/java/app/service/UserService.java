package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.user.add.AddUserForm;
import app.model.User;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Userを追加する
     */
    @Transactional
    public User addUser(AddUserForm addUserForm) throws EntityExistsException {
        // 同じメールアドレスのUserが存在する場合は例外を投げる
        boolean existEmail = userRepository.findByEmail(addUserForm.getUser().getEmail()).isPresent();
        if (existEmail) {
            throw new EntityExistsException("既に存在するメールアドレスです");
        }

        // フォームの値でUserオブジェクトを作成
        User addUser = new User(addUserForm);
        // Userを追加し、結果を返す
        return userRepository.save(addUser);
    }
}
