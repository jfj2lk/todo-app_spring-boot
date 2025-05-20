package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.form.user.LoginForm;
import app.form.user.SignUpForm;
import app.model.User;
import app.repository.UserRepository;
import app.utils.PasswordUtils;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    /**
     * 新規登録処理。
     */
    public User signup(SignUpForm signUpForm) throws RuntimeException {
        // 同じメールアドレスのUserが既に存在する場合は例外を投げる
        userRepository.findByEmail(signUpForm.getEmail()).ifPresent(user -> {
            throw new RuntimeException("既に存在するメールアドレスです。");
        });
        // フォームの値でUserオブジェクトを作成
        User signUpUser = new User(signUpForm);
        // パスワード暗号化
        signUpUser.setPassword(passwordUtils.encode(signUpUser.getPassword()));

        // Userを追加し、結果を返す
        return userRepository.save(signUpUser);
    }

    /**
     * ログイン処理。
     */
    public User login(LoginForm loginForm) throws RuntimeException {
        // メールアドレスが一致するユーザーを取得
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new RuntimeException("メールアドレスかパスワードが間違っています。"));
        // パスワードが一致するかチェック
        if (!passwordUtils.matches(loginForm.getPassword(), user.getPassword())) {
            throw new RuntimeException("メールアドレスかパスワードが間違っています。");
        }

        // メールアドレスが一致するUserを返す
        return user;
    }
}
