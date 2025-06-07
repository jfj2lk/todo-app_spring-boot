package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import app.form.label.AddLabelForm;
import app.form.label.UpdateLabelForm;
import app.model.Label;
import app.repository.LabelRepository;
import app.seeder.TestLabelSeeder;
import app.seeder.TestUserSeeder;
import app.utils.TestUtils;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LabelControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestUserSeeder testUserSeeder;
  @Autowired
  private TestLabelSeeder testLabelSeeder;
  @Autowired
  private LabelRepository labelRepository;
  @Autowired
  private TestUtils testUtils;

  private String jwt;

  @BeforeEach
  void setUpEach() {
    jwt = this.testUtils.createJwt(1L);
    testUserSeeder.seedInitialUser();
    List<Label> labels = List.of(new Label(1L, "label1"), new Label(1L, "label2"));
    labelRepository.saveAll(labels);
  }

  @Test
  void 全てのLabelを取得() throws Exception {
    // ユーザーに紐づく全てのLabelを取得
    List<Label> allLabelsForUser = testUtils.toList(labelRepository.findAllByUserId(1L));
    // 検証用のユーザーに紐づく全てのLabelの数を取得
    long expectedLabelCountForUser = allLabelsForUser.size();
    // 検証用のLabelを取得
    Label expectedLabel = allLabelsForUser.get(0);

    mockMvc.perform(get("/api/labels")
        .header("Authorization", "Bearer " + this.jwt))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.data.length()").value(expectedLabelCountForUser),
            jsonPath("$.data.[0].id").value(expectedLabel.getId()),
            jsonPath("$.data.[0].name").value(expectedLabel.getName()),
            jsonPath("$.data.[0].createdAt").exists(),
            jsonPath("$.data.[0].updatedAt").exists());
  }

  @Test
  void Labelを追加() throws Exception {
    // 追加されたLabelの期待するID
    long expectedAddLabelId = this.labelRepository.count() + 1;
    // Label追加後の期待するLabelの数
    long expectedTotalLabelCount = this.labelRepository.count() + 1;

    // Label追加用のフォームを作成
    AddLabelForm addLabelForm = new AddLabelForm("addLabel");
    String addLabelFormJson = this.testUtils.toJson(addLabelForm);

    mockMvc.perform(post("/api/labels")
        .header("Authorization", "Bearer " + this.jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(addLabelFormJson))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.data.id").value(expectedAddLabelId),
            jsonPath("$.data.name").value(addLabelForm.getName()),
            jsonPath("$.data.createdAt").exists(),
            jsonPath("$.data.updatedAt").exists());

    // レコードが1件分増えていることを確認
    assertEquals(expectedTotalLabelCount, this.labelRepository.count());
  }

  @Test
  void Labelを更新() throws Exception {
    // 更新するLabelのID
    long updateLabelId = 1L;
    // Label更新後の期待するLabelの数
    long expectedTotalLabelCount = this.labelRepository.count();

    // Label更新用のフォームを作成
    UpdateLabelForm updateLabelForm = new UpdateLabelForm("updateLabel");
    String updateLabelFormJson = this.testUtils.toJson(updateLabelForm);

    mockMvc.perform(patch("/api/labels/" + updateLabelId)
        .header("Authorization", "Bearer " + this.jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateLabelFormJson))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.data.id").value(updateLabelId),
            jsonPath("$.data.name").value(updateLabelForm.getName()),
            jsonPath("$.data.createdAt").exists(),
            jsonPath("$.data.updatedAt").exists());

    // レコードの件数が変わっていないことを確認
    assertEquals(expectedTotalLabelCount, this.labelRepository.count());
  }

  @Test
  void Labelを削除() throws Exception {
    // 削除するLabelのID
    long deleteLabelId = 1L;
    // Label削除後の期待するLabelの数
    long expectedTotalLabelCount = this.labelRepository.count() - 1;

    mockMvc.perform(delete("/api/labels/" + deleteLabelId)
        .header("Authorization", "Bearer " + this.jwt))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.data").value(deleteLabelId));

    // レコードが1件分減っていることを確認
    assertEquals(expectedTotalLabelCount, this.labelRepository.count());
  }
}
