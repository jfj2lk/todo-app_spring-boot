package app.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = true, length = 255)
    private String desc;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    /**
     * 指定した名前、説明を持つTodoオブジェクトを作成する
     */
    public Todo(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * Todo追加フォームの値でTodoオブジェクトを作成する
     */
    public Todo(AddTodoForm addTodoForm) {
        this.name = addTodoForm.getName();
        this.desc = addTodoForm.getDesc();
    }

    /**
     * Todo更新フォームの値でプロパティの値を更新する
     */
    public void updateWithForm(UpdateTodoForm updateTodoForm) {
        this.name = updateTodoForm.getName();
        this.desc = updateTodoForm.getDesc();
    }
}
