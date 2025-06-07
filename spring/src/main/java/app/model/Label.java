package app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import app.form.label.AddLabelForm;
import app.form.label.UpdateLabelForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label {
  @Id
  private Long id;
  private Long userId;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Label(Long userId, String name) {
    LocalDateTime now = LocalDateTime.now();
    this.userId = userId;
    this.name = name;
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Label追加フォームの値でLabelオブジェクトを作成する。
   */
  public Label(AddLabelForm addLabelForm, Long userId) {
    LocalDateTime now = LocalDateTime.now();
    this.userId = userId;
    this.name = addLabelForm.getName();
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Label更新フォームの値でLabelオブジェクトを更新する。
   */
  public void updateWithForm(UpdateLabelForm updateLabelForm) {
    LocalDateTime now = LocalDateTime.now();
    this.name = updateLabelForm.getName();
    this.updatedAt = now;
  }
}
