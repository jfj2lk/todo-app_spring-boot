package app.form.todo.add;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddTodoForm {
    @Valid
    @NotNull(message = "todoは必須です")
    private AddTodoInput todo;
}