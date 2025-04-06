package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.user.add.AddUserForm;
import app.model.User;
import app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Userを追加する
     */
    @Transactional
    public User addUser(AddUserForm addUserForm) {
        // フォームの値でUserオブジェクトを作成する
        User addUser = new User(addUserForm);
        return userRepository.save(addUser);
    }
}
