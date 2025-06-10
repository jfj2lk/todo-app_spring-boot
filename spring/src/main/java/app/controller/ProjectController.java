package app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.form.project.CreateProjectForm;
import app.form.project.UpdateProjectForm;
import app.model.Project;
import app.service.ProjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    /**
     * Project取得。
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Map<String, Project>> get(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long projectId) {
        Project project = projectService.get(projectId, Long.valueOf(userId));
        return ResponseEntity.ok().body(Map.of("data", project));
    }

    /**
     * 全てのProject取得。
     */
    @GetMapping("/projects")
    public ResponseEntity<Map<String, List<Project>>> getAll(
            @AuthenticationPrincipal String userId) {
        System.err.println(userId);
        List<Project> projects = projectService.getAll(Long.valueOf(userId));
        return ResponseEntity.ok().body(Map.of("data", projects));
    }

    /**
     * Project作成。
     */
    @PostMapping("/projects")
    public ResponseEntity<Map<String, Project>> create(
            @AuthenticationPrincipal String userId,
            @RequestBody @Validated CreateProjectForm createProjectForm) {
        Project createdProject = projectService.create(Long.valueOf(userId), createProjectForm);
        return ResponseEntity.ok().body(Map.of("data", createdProject));
    }

    /**
     * Project更新。
     */
    @PatchMapping("/projects/{id}")
    public ResponseEntity<Map<String, Project>> update(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long projectId,
            @RequestBody @Validated UpdateProjectForm UpdateProjectForm) {
        Project updatedProject = projectService.update(projectId, Long.valueOf(userId), UpdateProjectForm);
        return ResponseEntity.ok().body(Map.of("data", updatedProject));
    }

    /**
     * Project削除。
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Map<String, Long>> delete(
            @AuthenticationPrincipal String userId,
            @PathVariable("id") Long projectId) {
        Long deletedProjectId = projectService.delete(projectId, Long.valueOf(userId));
        return ResponseEntity.ok().body(Map.of("data", deletedProjectId));
    }
}
