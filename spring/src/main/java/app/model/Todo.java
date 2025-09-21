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
import org.springframework.data.relational.core.mapping.Table;

import app.form.todo.CreateTodoForm;
import app.form.todo.UpdateTodoForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("todos")
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
    private String description;
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
     * Todo作成フォームの値でTodoオブジェクトを作成する。
     */
    public Todo(CreateTodoForm createTodoForm, Long userId) {
        this.userId = userId;
        this.isCompleted = false;
        this.name = createTodoForm.getName();
        this.description = createTodoForm.getDescription();
        this.priority = createTodoForm.getPriority();
        this.dueDate = createTodoForm.getDueDate();
        this.dueTime = createTodoForm.getDueTime();
        this.todoLabels = createTodoForm.getLabelIds().stream()
                .map(TodoLabel::new)
                .collect(Collectors.toSet());
    }

    /**
     * Todo作成フォームの値でProjectに紐づくTodoオブジェクトを作成する。
     */
    public Todo(CreateTodoForm createTodoForm, Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
        this.isCompleted = false;
        this.name = createTodoForm.getName();
        this.description = createTodoForm.getDescription();
        this.priority = createTodoForm.getPriority();
        this.dueDate = createTodoForm.getDueDate();
        this.dueTime = createTodoForm.getDueTime();
        this.todoLabels = createTodoForm.getLabelIds().stream()
                .map(TodoLabel::new)
                .collect(Collectors.toSet());
    }

    /**
     * Todo更新フォームの値でTodoオブジェクトを更新する。
     */
    public void updateWithForm(UpdateTodoForm updateTodoForm) {
        this.name = updateTodoForm.getName();
        this.description = updateTodoForm.getDescription();
        this.priority = updateTodoForm.getPriority();
        this.dueDate = updateTodoForm.getDueDate();
        this.dueTime = updateTodoForm.getDueTime();
        this.todoLabels = updateTodoForm.getLabelIds().stream()
                .map(TodoLabel::new)
                .collect(Collectors.toSet());
    }
}
