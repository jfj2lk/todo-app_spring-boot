package app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
  @Id
  private Long id;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Project(String name) {
    LocalDateTime now = LocalDateTime.now();
    this.name = name;
    this.createdAt = now;
    this.updatedAt = now;
  }
}
