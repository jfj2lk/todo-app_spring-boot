package app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;

import app.form.project.CreateProjectForm;
import app.form.project.UpdateProjectForm;
import app.model.Project;
import app.repository.ProjectRepository;
import app.seeder.Seeder;
import app.utils.TestUtils;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/reset-sequence.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Seeder seeder;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TestUtils testUtils;

    private final Long userId = 1L;
    private String jwt;

    @BeforeEach
    void setUpEach() {
        // JWT作成
        jwt = testUtils.createJwt(userId);
        // 初期データ作成
        seeder.seedInitialData();
    }

    /**
     * Userの全てのProjectを取得するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void getAllProjects() throws Exception {
        // API実行前の全てのProjectを取得
        List<Project> projectsCountBeforeApi = projectRepository.findAll();

        // 期待値となるUserの全てのProjectを取得
        List<Project> expectedUserProjects = projectRepository.findAllByUserId(userId);

        // API実行
        String json = mockMvc
                .perform(get("/api/projects")
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        List<Project> responseUserProjects = testUtils.fieldFromJson(json, "data", new TypeReference<List<Project>>() {
        });

        /* レスポンス検証 */
        // Projectの数が期待値と一致するか確認
        assertThat(responseUserProjects).hasSameSizeAs(expectedUserProjects);
        // 内容が期待値と一致するか確認
        assertThat(responseUserProjects)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserProjects);

        /* DB検証 */
        // Projectのレコード数が変わっていないことを確認
        List<Project> projectsCountAfterApi = projectRepository.findAll();
        assertThat(projectsCountAfterApi).hasSize(projectsCountBeforeApi.size());
    }

    /**
     * Projectを作成するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void createProject() throws Exception {
        // API実行前の全てのProjectを取得
        List<Project> projectsCountBeforeApi = projectRepository.findAll();

        // Project作成用フォームを作成
        CreateProjectForm createProjectForm = new CreateProjectForm("createProject");
        // フォームをJsonに変換
        String createProjectFormJson = testUtils.toJson(createProjectForm);

        // API実行
        String json = mockMvc
                .perform(post("/api/projects")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createProjectFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Project responseCreatedProject = testUtils.fieldFromJson(json, "data", Project.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseCreatedProject.getId()).isNotNull();
        assertThat(responseCreatedProject.getUserId()).isEqualTo(userId);
        assertThat(responseCreatedProject.getName()).isEqualTo(createProjectForm.getName());
        assertThat(responseCreatedProject.getCreatedAt()).isNotNull();
        assertThat(responseCreatedProject.getUpdatedAt()).isNotNull();

        /* DB検証 */
        // Projectのレコード数が1件増えていることを確認
        List<Project> projectsCountAfterApi = projectRepository.findAll();
        assertThat(projectsCountAfterApi).hasSize(projectsCountBeforeApi.size() + 1);
    }

    /**
     * Projectを更新するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void updateProject() throws Exception {
        // API実行前の全てのProjectを取得
        List<Project> projectsCountBeforeApi = projectRepository.findAllByUserId(userId);

        // 更新対象のProjectId
        Long updateProjectId = 1L;
        // Project作成用フォームを作成
        UpdateProjectForm updateProjectForm = new UpdateProjectForm("updateProject");
        // フォームをJsonに変換
        String updateProjectFormJson = testUtils.toJson(updateProjectForm);

        // API実行
        String json = mockMvc
                .perform(patch("/api/projects/" + updateProjectId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateProjectFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Project responseUpdatedProject = testUtils.fieldFromJson(json, "data", Project.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseUpdatedProject.getId()).isNotNull();
        assertThat(responseUpdatedProject.getUserId()).isEqualTo(userId);
        assertThat(responseUpdatedProject.getName()).isEqualTo(updateProjectForm.getName());
        assertThat(responseUpdatedProject.getCreatedAt()).isNotNull();
        assertThat(responseUpdatedProject.getUpdatedAt()).isNotNull();
        // 更新日時が作成日時よりも後か確認
        assertThat(responseUpdatedProject.getCreatedAt()).isBeforeOrEqualTo(responseUpdatedProject.getUpdatedAt());

        /* DB検証 */
        // Projectのレコード数が変わっていないことを確認
        List<Project> projectsCountAfterApi = projectRepository.findAll();
        assertThat(projectsCountAfterApi).hasSize(projectsCountBeforeApi.size());
    }

    /**
     * Projectを削除するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void deleteProject() throws Exception {
        // API実行前の全てのProjectを取得
        List<Project> projectsCountBeforeApi = projectRepository.findAllByUserId(userId);

        // 削除対象のProjectId
        Long deleteProjectId = 1L;

        // API実行
        String json = mockMvc
                .perform(delete("/api/projects/" + deleteProjectId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Long responseDeletedProjectId = testUtils.fieldFromJson(json, "data", Long.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseDeletedProjectId).isEqualTo(deleteProjectId);

        /* DB検証 */
        // 削除対象のレコードが見つからないことを確認
        assertTrue(projectRepository.findById(responseDeletedProjectId).isEmpty());
        // Projectのレコード数が1件分減っていることを確認
        List<Project> projectsCountAfterApi = projectRepository.findAll();
        assertThat(projectsCountAfterApi).hasSize(projectsCountBeforeApi.size() - 1);
    }
}
