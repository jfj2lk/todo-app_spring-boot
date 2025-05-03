package app.service;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.form.user.LoginForm;
import app.form.user.signup.SignUpForm;
import app.model.User;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * Userを追加する
     */
    @Transactional
    public User signup(SignUpForm addUserForm) throws EntityExistsException {
        // 同じメールアドレスのUserが存在する場合は例外を投げる
        boolean existEmail =
                userRepository.findByEmail(addUserForm.getUser().getEmail()).isPresent();
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
        // メールアドレスが一致するユーザーを取得
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("メールアドレスかパスワードが間違っています。"));
        // パスワードが一致するかチェック
        boolean isPasswordMatch =
                passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new EntityNotFoundException("メールアドレスかパスワードが間違っています。");
        }
        return user;
    }

}
