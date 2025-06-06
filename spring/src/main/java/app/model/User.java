package app.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import app.form.user.SignUpForm;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * 指定した名前、メールアドレス、パスワードを持つUserを作成する
     */
    public User(String name, String email, String password) {
        LocalDateTime now = LocalDateTime.now();
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * サインアップフォームの値でUserを作成する
     */
    public User(SignUpForm signUpForm) {
        LocalDateTime now = LocalDateTime.now();
        this.name = signUpForm.getName();
        this.email = signUpForm.getEmail();
        this.password = signUpForm.getPassword();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
