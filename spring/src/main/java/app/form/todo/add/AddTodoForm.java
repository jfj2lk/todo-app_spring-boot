package app.form.todo.add;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTodoForm {
    @NotNull(message = "todoは必須です")
    private AddTodoInput todo;
}