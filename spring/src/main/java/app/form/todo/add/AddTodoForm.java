package app.form.todo.add;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTodoForm {
    @Valid
    @NotNull(message = "todoは必須です")
    private AddTodoInput todo;
}