package app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.exception.ModelNotFoundException;
import app.form.project.CreateProjectForm;
import app.form.project.UpdateProjectForm;
import app.model.Project;
import app.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    /**
     * Project取得。
     */
    public Project get(Long projectId, Long userId) {
        return projectRepository
                .findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
    }

    /**
     * 全てのProject取得。
     */
    public List<Project> getAll(Long userId) {
        return projectRepository.findAllByUserId(userId);
    }

    /**
     * Project作成。
     */
    public Project create(Long userId, CreateProjectForm createProjectForm) {
        Project createProject = new Project(createProjectForm, userId);
        return projectRepository.save(createProject);
    }

    /**
     * Project更新。
     */
    public Project update(Long projectId, Long userId, UpdateProjectForm updateProjectForm) {
        Project updateProject = projectRepository
                .findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        updateProject.updateWithForm(updateProjectForm);
        return projectRepository.save(updateProject);
    }

    /**
     * Project削除。
     */
    public Long delete(Long projectId, Long userId) {
        Project deleteProject = projectRepository
                .findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたProjectが見つかりません。"));
        projectRepository.delete(deleteProject);
        return projectId;
    }
}
