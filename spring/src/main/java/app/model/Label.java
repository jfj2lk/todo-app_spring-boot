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
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Label(String name) {
    LocalDateTime now = LocalDateTime.now();
    this.name = name;
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Labelフォームの値でLabelオブジェクトを作成する。
   */
  public Label(AddLabelForm addLabelForm) {
    LocalDateTime now = LocalDateTime.now();
    this.name = addLabelForm.getName();
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Labelフォームの値でLabelオブジェクトを作成する。
   */
  public void updateWithForm(UpdateLabelForm updateLabelForm) {
    LocalDateTime now = LocalDateTime.now();
    this.name = updateLabelForm.getName();
    this.createdAt = now;
    this.updatedAt = now;
  }
}
