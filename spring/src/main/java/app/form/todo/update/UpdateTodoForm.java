package app.form.todo.update;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTodoForm {
    @NotNull(message = "todoは必須です")
    private UpdateTodoInput todo;
}