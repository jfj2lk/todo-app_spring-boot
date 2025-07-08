package app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import app.form.user.SignUpForm;
import app.form.user.UpdateUserForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * User追加フォームの値でUserオブジェクトを作成する。
     */
    public User(SignUpForm signUpForm) {
        this.name = signUpForm.getName();
        this.email = signUpForm.getEmail();
        this.password = signUpForm.getPassword();
    }

    /**
     * User更新フォームの値でUserオブジェクトを作成する。
     */
    public void updateWithForm(UpdateUserForm updateUserForm) {
        this.name = updateUserForm.getName();
        this.email = updateUserForm.getEmail();
        this.password = updateUserForm.getPassword();
    }
}
