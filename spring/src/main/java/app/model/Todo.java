package app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalDate dueDate;
    private LocalTime dueTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 指定可能なプロパティ（ユーザーID、完了フラグ、名前、説明、優先度、期限日時）を全て指定したTodoオブジェクトを作成する。
     */
    public Todo(Long userId, Boolean isCompleted, String name, String desc, Integer priority,
            LocalDate dueDate, LocalTime dueTime) {
        LocalDateTime now = LocalDateTime.now();
        this.userId = userId;
        this.isCompleted = isCompleted;
        this.name = name;
        this.desc = desc;
        this.priority = priority;
        this.createdAt = now;
        this.updatedAt = now;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    /**
     * Todo追加フォームの値でTodoオブジェクトを作成する
     */
    public Todo(AddTodoForm addTodoForm, Long loginUserId) {
        LocalDateTime now = LocalDateTime.now();
        this.userId = loginUserId;
        this.isCompleted = false;
        this.name = addTodoForm.getName();
        this.desc = addTodoForm.getDesc();
        this.priority = addTodoForm.getPriority();
        this.dueDate = addTodoForm.getDueDate();
        this.dueTime = addTodoForm.getDueTime();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Todo更新フォームの値でプロパティの値を更新する
     */
    public void updateWithForm(UpdateTodoForm updateTodoForm) {
        LocalDateTime now = LocalDateTime.now();
        this.name = updateTodoForm.getName();
        this.desc = updateTodoForm.getDesc();
        this.priority = updateTodoForm.getPriority();
        this.dueDate = updateTodoForm.getDueDate();
        this.dueTime = updateTodoForm.getDueTime();
        this.updatedAt = now;
    }
}
