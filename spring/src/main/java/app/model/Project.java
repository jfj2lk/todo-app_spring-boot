package app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import app.form.project.AddProjectForm;
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
   * Project追加フォームの値でProjectオブジェクトを作成する。
   */
  public Project(AddProjectForm addProjectForm, Long userId) {
    this.userId = userId;
    this.name = addProjectForm.getName();
  }

  /**
   * Project更新フォームの値でProjectオブジェクトを更新する。
   */
  public void updateWithForm(UpdateProjectForm updateProjectForm) {
    this.name = updateProjectForm.getName();
  }
}
