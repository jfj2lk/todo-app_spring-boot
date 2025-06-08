package app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Set;

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

import app.form.project.AddProjectForm;
import app.form.project.UpdateProjectForm;
import app.model.Project;
import app.repository.ProjectRepository;
import app.seeder.TestProjectSeeder;
import app.seeder.TestUserSeeder;
import app.utils.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/reset-sequence.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestUtils testUtils;

  private Long operatorId = 1L;
  private String jwt;

  // シーダー
  @Autowired
  private TestUserSeeder testUserSeeder;
  @Autowired
  private TestProjectSeeder testProjectSeeder;

  // リポジトリ
  @Autowired
  private ProjectRepository projectRepository;

  @BeforeEach
  void setUpEach() {
    jwt = testUtils.createJwt(operatorId);
    // 初期データ作成
    testUserSeeder.seedInitialUser();
    testProjectSeeder.initialSeed();
  }

  /**
   * 全てのProjectを取得するテスト。
   * ユーザーに紐づく全件のProjectが取得できている事と、レスポンスの形式が正しいかを確認する。
   */
  @Test
  void getAllProjects() throws Exception {
    // ユーザーに紐づく全てのProjectを取得
    List<Project> allProjectsForUser = testUtils.toList(projectRepository.findAllByUserId(operatorId));
    // 検証用のユーザーに紐づく全てのProjectの数を取得
    long expectedProjectCountForUser = allProjectsForUser.size();
    // 検証用のProjectを取得
    Project expectedProject = allProjectsForUser.get(0);

    // ユーザーに紐づく全てのProjectを取得
    String json = mockMvc
        .perform(get("/api/projects")
            .header("Authorization", "Bearer " + this.jwt))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON))
        // ユーザーに紐づくProjectが全件取得できているか確認
        .andExpect(jsonPath("$.data.length()").value(expectedProjectCountForUser))
        .andReturn().getResponse().getContentAsString();

    // Jsonから最初のProjectを取得
    Project actualProject = testUtils.fieldIndexFromJson(json, "data", 0, Project.class);

    // レスポンスのProjectの形式が正しいか確認
    assertThat(actualProject).usingRecursiveComparison()
        .ignoringFields("createdAt", "updatedAt")
        .withEqualsForType(Object::equals, Set.class)
        .isEqualTo(expectedProject);
    assertThat(actualProject)
        .extracting(Project::getCreatedAt, Project::getUpdatedAt)
        .doesNotContainNull();
  }

  @Test
  void addProject() throws Exception {
    // 追加されたProjectの期待するID
    long expectedAddProjectId = this.projectRepository.count() + 1;
    // Project追加後の期待するProjectの数
    long expectedTotalProjectCount = this.projectRepository.count() + 1;

    // Project追加用のフォームを作成
    AddProjectForm addProjectForm = new AddProjectForm("addProject");
    String addProjectFormJson = this.testUtils.toJson(addProjectForm);

    String json = mockMvc.perform(post("/api/projects")
        .header("Authorization", "Bearer " + this.jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(addProjectFormJson))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString();

    // Jsonから最初のProjectを取得
    Project actualProject = testUtils.fieldFromJson(json, "data", Project.class);

    // ユーザーに紐づく全てのProjectを取得
    List<Project> allProjectsForUser = testUtils.toList(projectRepository.findAllByUserId(operatorId));
    // 検証用のProjectを取得
    Project expectedProject = allProjectsForUser.get(allProjectsForUser.size() - 1);

    // レスポンスのProjectの形式が正しいか確認
    assertThat(actualProject).usingRecursiveComparison()
        .ignoringFields("createdAt", "updatedAt")
        .withEqualsForType(Object::equals, Set.class)
        .isEqualTo(expectedProject);
    assertThat(actualProject)
        .extracting(Project::getCreatedAt, Project::getUpdatedAt)
        .doesNotContainNull();

    // レコードが1件分増えていることを確認
    assertEquals(expectedTotalProjectCount, this.projectRepository.count());
  }

  @Test
  void updateProject() throws Exception {
    // 更新するProjectのID
    long updateProjectId = 1L;
    // Project更新後の期待するProjectの数
    long expectedTotalProjectCount = this.projectRepository.count();

    // Project更新用のフォームを作成
    UpdateProjectForm updateProjectForm = new UpdateProjectForm("updateProject");
    String updateProjectFormJson = this.testUtils.toJson(updateProjectForm);

    mockMvc.perform(patch("/api/projects/" + updateProjectId)
        .header("Authorization", "Bearer " + this.jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateProjectFormJson))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.data.id").value(updateProjectId),
            jsonPath("$.data.name").value(updateProjectForm.getName()),
            jsonPath("$.data.createdAt").exists(),
            jsonPath("$.data.updatedAt").exists());

    // レコードの件数が変わっていないことを確認
    assertEquals(expectedTotalProjectCount, this.projectRepository.count());
  }

  @Test
  void deleteProject() throws Exception {
    // 削除するProjectのID
    long deleteProjectId = 1L;
    // Project削除後の期待するProjectの数
    long expectedTotalProjectCount = this.projectRepository.count() - 1;

    mockMvc.perform(delete("/api/projects/" + deleteProjectId)
        .header("Authorization", "Bearer " + this.jwt))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.data").value(deleteProjectId));

    // レコードが1件分減っていることを確認
    assertEquals(expectedTotalProjectCount, this.projectRepository.count());
  }
}
