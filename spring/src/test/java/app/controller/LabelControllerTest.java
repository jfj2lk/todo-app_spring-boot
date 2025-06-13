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

import app.form.label.CreateLabelForm;
import app.form.label.UpdateLabelForm;
import app.model.Label;
import app.repository.LabelRepository;
import app.seeder.Seeder;
import app.utils.TestUtils;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/reset-sequence.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Seeder seeder;
    @Autowired
    private LabelRepository labelRepository;
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
     * Userの全てのLabelを取得するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void getAllLabels() throws Exception {
        // API実行前の全てのLabelを取得
        List<Label> labelsCountBeforeApi = labelRepository.findAll();

        // 期待値となるUserの全てのLabelを取得
        List<Label> expectedUserLabels = labelRepository.findAllByUserId(userId);

        // API実行
        String json = mockMvc
                .perform(get("/api/labels")
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        List<Label> responseUserLabels = testUtils.fromJsonFieldAsList(json, "data", Label.class);

        /* レスポンス検証 */
        // Labelの数が期待値と一致するか確認
        assertThat(responseUserLabels).hasSameSizeAs(expectedUserLabels);
        // 内容が期待値と一致するか確認
        assertThat(responseUserLabels)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserLabels);

        /* DB検証 */
        // Labelのレコード数が変わっていないことを確認
        List<Label> labelsCountAfterApi = labelRepository.findAll();
        assertThat(labelsCountAfterApi).hasSize(labelsCountBeforeApi.size());
    }

    /**
     * Labelを作成するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void createLabel() throws Exception {
        // API実行前の全てのLabelを取得
        List<Label> labelsCountBeforeApi = labelRepository.findAll();

        // Label作成用フォームを作成
        CreateLabelForm createLabelForm = new CreateLabelForm("createLabel");
        // フォームをJsonに変換
        String createLabelFormJson = testUtils.toJson(createLabelForm);

        // API実行
        String json = mockMvc
                .perform(post("/api/labels")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createLabelFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Label responseCreatedLabel = testUtils.fromJsonField(json, "data", Label.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseCreatedLabel.getId()).isNotNull();
        assertThat(responseCreatedLabel.getUserId()).isEqualTo(userId);
        assertThat(responseCreatedLabel.getName()).isEqualTo(createLabelForm.getName());
        assertThat(responseCreatedLabel.getCreatedAt()).isNotNull();
        assertThat(responseCreatedLabel.getUpdatedAt()).isNotNull();

        /* DB検証 */
        // Labelのレコード数が1件増えていることを確認
        List<Label> labelsCountAfterApi = labelRepository.findAll();
        assertThat(labelsCountAfterApi).hasSize(labelsCountBeforeApi.size() + 1);
    }

    /**
     * Labelを更新するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void updateLabel() throws Exception {
        // API実行前の全てのLabelを取得
        List<Label> labelsCountBeforeApi = labelRepository.findAllByUserId(userId);

        // 更新対象のLabelId
        Long updateLabelId = 1L;
        // Label作成用フォームを作成
        UpdateLabelForm updateLabelForm = new UpdateLabelForm("updateLabel");
        // フォームをJsonに変換
        String updateLabelFormJson = testUtils.toJson(updateLabelForm);

        // API実行
        String json = mockMvc
                .perform(patch("/api/labels/" + updateLabelId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateLabelFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Label responseUpdatedLabel = testUtils.fromJsonField(json, "data", Label.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseUpdatedLabel.getId()).isNotNull();
        assertThat(responseUpdatedLabel.getUserId()).isEqualTo(userId);
        assertThat(responseUpdatedLabel.getName()).isEqualTo(updateLabelForm.getName());
        assertThat(responseUpdatedLabel.getCreatedAt()).isNotNull();
        assertThat(responseUpdatedLabel.getUpdatedAt()).isNotNull();
        // 更新日時が作成日時よりも後か確認
        assertThat(responseUpdatedLabel.getCreatedAt()).isBeforeOrEqualTo(responseUpdatedLabel.getUpdatedAt());

        /* DB検証 */
        // Labelのレコード数が変わっていないことを確認
        List<Label> labelsCountAfterApi = labelRepository.findAll();
        assertThat(labelsCountAfterApi).hasSize(labelsCountBeforeApi.size());
    }

    /**
     * Labelを削除するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void deleteLabel() throws Exception {
        // API実行前の全てのLabelを取得
        List<Label> labelsCountBeforeApi = labelRepository.findAllByUserId(userId);

        // 削除対象のLabelId
        Long deleteLabelId = 1L;

        // API実行
        String json = mockMvc
                .perform(delete("/api/labels/" + deleteLabelId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Long responseDeletedLabelId = testUtils.fromJsonField(json, "data", Long.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseDeletedLabelId).isEqualTo(deleteLabelId);

        /* DB検証 */
        // 削除対象のレコードが見つからないことを確認
        assertTrue(labelRepository.findById(responseDeletedLabelId).isEmpty());
        // Labelのレコード数が1件分減っていることを確認
        List<Label> labelsCountAfterApi = labelRepository.findAll();
        assertThat(labelsCountAfterApi).hasSize(labelsCountBeforeApi.size() - 1);
    }
}
