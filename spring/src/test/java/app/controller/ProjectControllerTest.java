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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import app.model.Project;
import app.repository.ProjectRepository;
import app.seeder.TestProjectSeeder;
import app.seeder.TestUserSeeder;
import app.utils.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProjectControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestUtils testUtils;

  // シーダー
  @Autowired
  private TestUserSeeder testUserSeeder;
  @Autowired
  private TestProjectSeeder testProjectSeeder;

  // リポジトリ
  @Autowired
  private ProjectRepository projectRepository;

  // その他
  private Long operatorId = 1L;
  private String jwt;

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
    List<Project> allProjectsForUser = testUtils.toList(projectRepository.findAllByUserId(1L));
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
}
