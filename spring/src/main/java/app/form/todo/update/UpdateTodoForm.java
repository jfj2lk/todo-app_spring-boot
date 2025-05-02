package app.form.todo.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoForm {
    @Valid
    @NotNull(message = "todoは必須です")
    private UpdateTodoFieldForm todo;
}
