package app.form.user.add;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserForm {

    @Valid
    @NotNull(message = "userは必須です")
    private AddUserInput user;

}
