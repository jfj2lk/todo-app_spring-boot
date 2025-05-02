package app.form.user.signup;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @Valid
    @NotNull(message = "userは必須です")
    private SignUpInput user;

}
