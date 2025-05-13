package app.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    private Long id;
    private Long userId;
    private Boolean isCompleted;
    private String name;
    private String desc;
    private Integer priority;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * 指定可能なプロパティ（ユーザーID、完了フラグ、名前、説明、優先度）を全て指定したTodoオブジェクトを作成する。
     */
    public Todo(Long userId, Boolean isCompleted, String name, String desc, Integer priority) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.userId = userId;
        this.isCompleted = isCompleted;
        this.name = name;
        this.desc = desc;
        this.priority = priority;
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Todo追加フォームの値でTodoオブジェクトを作成する
     */
    public Todo(AddTodoForm addTodoForm, Long loginUserId) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        this.userId = loginUserId;
        this.isCompleted = false;
        this.name = addTodoForm.getName();
        this.desc = addTodoForm.getDesc();
        this.priority = addTodoForm.getPriority();
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
        this.priority = updateTodoForm.getPriority();
        this.updatedAt = now;
    }
}
