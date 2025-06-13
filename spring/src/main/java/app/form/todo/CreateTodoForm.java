package app.form.todo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodoForm {
    @NotBlank(message = "nameは必須です")
    @Size(max = 255, message = "nameは255文字以内で入力してください")
    private String name;

    @Size(max = 255, message = "descは255文字以内で入力してください")
    private String desc;

    @NotNull(message = "priorityは必須です")
    @Min(value = 1, message = "priorityは1以上にしてください")
    @Max(value = 4, message = "priorityは4以下にしてください")
    private Integer priority;

    private LocalDate dueDate;

    private LocalTime dueTime;

    private Set<Long> labelIds;
}
