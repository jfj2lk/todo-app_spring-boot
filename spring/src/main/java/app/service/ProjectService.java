package app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.project.AddProjectForm;
import app.form.project.UpdateProjectForm;
import app.model.Project;
import app.repository.ProjectRepository;
import app.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
  private final ProjectRepository projectRepository;
  private final SecurityUtils securityUtils;

  /**
   * ユーザーに紐づく全てのProjectを取得する。
   */
  public Iterable<Project> getAllProjects() {
    Long loginUserId = securityUtils.getCurrentUserId();
    return projectRepository.findAllByUserId(loginUserId);
  }

  /**
   * Projectを追加する。
   */
  public Project addProject(AddProjectForm addProjectForm) {
    Long loginUserId = securityUtils.getCurrentUserId();
    Project addProject = new Project(addProjectForm, loginUserId);
    return projectRepository.save(addProject);
  }

  /**
   * Projectを更新する。
   */
  public Project updateProject(Long updateProjectId, UpdateProjectForm updateProjectForm)
      throws RuntimeException {
    Long loginUserId = securityUtils.getCurrentUserId();
    Project updateProject = projectRepository.findByIdAndUserId(updateProjectId, loginUserId)
        .orElseThrow(() -> new RuntimeException("更新対象のProjectが見つかりませんでした。"));
    // フォームの値でProjectの値を更新する
    updateProject.updateWithForm(updateProjectForm);
    return projectRepository.save(updateProject);
  }

  /**
   * Projectを削除する。
   */
  public Long deleteProject(Long deleteProjectId) throws RuntimeException {
    Long loginUserId = securityUtils.getCurrentUserId();

    Optional<Project> deleteProjectOptional = projectRepository.findByIdAndUserId(deleteProjectId, loginUserId);
    deleteProjectOptional.ifPresentOrElse(
        project -> projectRepository.delete(project),
        () -> {
          throw new RuntimeException("削除対象のProjectが見つかりません。");
        });

    return deleteProjectId;
  }
}
