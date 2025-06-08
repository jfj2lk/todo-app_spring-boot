package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.form.project.AddProjectForm;
import app.form.project.UpdateProjectForm;
import app.model.Project;
import app.service.ProjectService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {
  private final ProjectService projectService;

  // 全てのProject取得
  @GetMapping("/projects")
  public ResponseEntity<Map<String, Iterable<Project>>> getAllProjects() {
    Iterable<Project> allProjects = projectService.getAllProjects();
    return ResponseEntity.ok().body(Map.of("data", allProjects));
  }

  // Project追加
  @PostMapping("/projects")
  public ResponseEntity<Map<String, Project>> addProject(@RequestBody @Validated AddProjectForm addProjectForm) {
    Project addedProject = projectService.addProject(addProjectForm);
    return ResponseEntity.ok().body(Map.of("data", addedProject));
  }

  // Project更新
  @PatchMapping("/projects/{id}")
  public ResponseEntity<Map<String, Project>> updateProject(
      @PathVariable("id") Long projectId,
      @RequestBody @Validated UpdateProjectForm UpdateProjectForm) {
    Project updatedProject = projectService.updateProject(projectId, UpdateProjectForm);
    return ResponseEntity.ok().body(Map.of("data", updatedProject));
  }

  // Project削除
  @DeleteMapping("/projects/{id}")
  public ResponseEntity<Map<String, Long>> deleteProject(@PathVariable("id") Long projectId) {
    Long deletedProjectId = projectService.deleteProject(projectId);
    return ResponseEntity.ok().body(Map.of("data", deletedProjectId));
  }

}
