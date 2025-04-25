package app.service;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.user.add.AddUserForm;
import app.form.user.login.LoginForm;
import app.form.user.login.LoginInput;
import app.model.User;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

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
        // パスワード暗号化
        String rawPassword = addUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        addUser.setPassword(encodedPassword);
        // Userを追加し、結果を返す
        return userRepository.save(addUser);
    }

    /**
     * ログイン処理
     */
    public User login(LoginForm loginForm) throws EntityNotFoundException {
        final LoginInput loginInput = loginForm.getUser();
        // メールアドレスが一致するユーザーを取得
        User user = userRepository.findByEmail(loginInput.getEmail()).get();
        // パスワードが一致するかチェック
        boolean isPasswordMatch = passwordEncoder.matches(loginInput.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new EntityNotFoundException("メールアドレスかパスワードが間違っています");
        }
        return user;
    }

}
