package app.form.todo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTodoForm {
    @NotNull(message = "todoは必須です")
    private TodoAddInput todo;
}