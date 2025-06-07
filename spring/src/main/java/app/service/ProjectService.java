package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
