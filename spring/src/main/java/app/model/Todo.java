package app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.MappedCollection;

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
    private Long projectId;
    private Boolean isCompleted;
    private String name;
    private String desc;
    private Integer priority;
    private LocalDate dueDate;
    private LocalTime dueTime;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @MappedCollection(idColumn = "TODO_ID", keyColumn = "LABEL_ID")
    private Set<TodoLabel> todoLabels = new LinkedHashSet<>();

    /**
     * Todo追加フォームの値でTodoオブジェクトを作成する。
     */
    public Todo(AddTodoForm addTodoForm, Long userId) {
        this.userId = userId;
        this.isCompleted = false;
        this.name = addTodoForm.getName();
        this.desc = addTodoForm.getDesc();
        this.priority = addTodoForm.getPriority();
        this.dueDate = addTodoForm.getDueDate();
        this.dueTime = addTodoForm.getDueTime();
        this.todoLabels = addTodoForm.getLabelIds().stream()
                .map(TodoLabel::new)
                .collect(Collectors.toSet());
    }

    /**
     * Todo更新フォームの値でTodoオブジェクトを更新する。
     */
    public void updateWithForm(UpdateTodoForm updateTodoForm) {
        this.name = updateTodoForm.getName();
        this.desc = updateTodoForm.getDesc();
        this.priority = updateTodoForm.getPriority();
        this.dueDate = updateTodoForm.getDueDate();
        this.dueTime = updateTodoForm.getDueTime();
        this.todoLabels = updateTodoForm.getLabelIds().stream()
                .map(TodoLabel::new)
                .collect(Collectors.toSet());
    }
}
