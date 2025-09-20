package app.form.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLabelForm {
    @NotBlank(message = "名前は必須です")
    @Size(max = 255, message = "名前は255文字以内で入力してください")
    private String name;
}
