package app.seeder;

import java.util.List;

import org.springframework.stereotype.Component;

import app.model.Project;
import app.repository.ProjectRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class TestProjectSeeder {
  private final ProjectRepository projectRepository;
  private List<Project> projects;

  public void initialSeed() {
    projects = List.of(
        new Project(null, 1L, "project1", null, null),
        new Project(null, 1L, "project2", null, null));
    projectRepository.saveAll(projects);
  }
}
