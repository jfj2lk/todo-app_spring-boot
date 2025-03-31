package app.form.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddTodoInput {
    @NotBlank(message = "nameは必須です")
    @Size(max = 255, message = "nameは255文字以内で入力してください")
    private String name;

    @Size(max = 255, message = "descは255文字以内で入力してください")
    private String desc;
}
