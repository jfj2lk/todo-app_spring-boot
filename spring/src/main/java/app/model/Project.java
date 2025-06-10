package app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import app.form.project.CreateProjectForm;
import app.form.project.UpdateProjectForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    private Long id;
    private Long userId;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Project作成フォームの値でProjectオブジェクトを作成する。
     */
    public Project(CreateProjectForm createProjectForm, Long userId) {
        this.userId = userId;
        this.name = createProjectForm.getName();
    }

    /**
     * Project更新フォームの値でProjectオブジェクトを更新する。
     */
    public void updateWithForm(UpdateProjectForm updateProjectForm) {
        this.name = updateProjectForm.getName();
    }
}
