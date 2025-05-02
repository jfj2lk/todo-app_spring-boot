package app.form.user.login;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @Valid
    @NotNull(message = "userは必須です")
    private LoginUserFieldForm user;

}
