package app.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    private Long id;

    @NotBlank
    private String name;
    private String desc;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * 指定した名前、説明を持つTodoオブジェクトを作成する
     */
    public Todo(String name, String desc) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.name = name;
        this.desc = desc;
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Todo追加フォームの値でTodoオブジェクトを作成する
     */
    public Todo(AddTodoForm addTodoForm) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.name = addTodoForm.getName();
        this.desc = addTodoForm.getDesc();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Todo更新フォームの値でプロパティの値を更新する
     */
    public void updateWithForm(UpdateTodoForm updateTodoForm) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.name = updateTodoForm.getName();
        this.desc = updateTodoForm.getDesc();
        this.updatedAt = now;
    }
}
