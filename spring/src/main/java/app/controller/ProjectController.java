package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.service.ProjectService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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

}
