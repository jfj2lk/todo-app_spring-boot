package app.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoLabel {
  @Id
  private Long id;
  private Long todoId;
  private Long labelId;

  public TodoLabel(Long todoId, Long labelId) {
    this.todoId = todoId;
    this.labelId = labelId;
  }
}
