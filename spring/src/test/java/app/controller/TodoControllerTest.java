package app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
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

import app.form.todo.CreateTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;
import app.seeder.Seeder;
import app.utils.TestUtils;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/reset-sequence.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Seeder seeder;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TestUtils testUtils;

    private final Long userId = 1L;
    private final Long projectId = 1L;
    private final Long labelId = 1L;
    private final String apiBasePath = "/api/todos";
    private final String queryParamProjectId = "?projectId=" + projectId;
    private final String queryParamLabelId = "?labelId=" + labelId;
    private String jwt;

    @BeforeEach
    void setUpEach() {
        // JWT作成
        jwt = testUtils.createJwt(userId);
        // 初期データ作成
        seeder.seedInitialData();
    }

    /**
     * 単一のTodoを取得するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void getTodo() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 取得対象のTodoId
        Long getTodoId = 1L;
        // 期待値となるTodoを取得
        Todo expectedTodo = todoRepository.findById(getTodoId).get();

        // API実行
        String json = mockMvc
                .perform(get(apiBasePath + "/" + getTodoId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Todo responseTodo = testUtils.fromJsonField(json, "data", Todo.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseTodo)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedTodo);

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }

    /**
     * 全てのTodoを取得するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void getAllTodos() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 期待値となる全てのTodoを取得
        List<Todo> expectedTodos = todoRepository.findAllByUserId(userId);

        // API実行
        String json = mockMvc
                .perform(get(apiBasePath)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        List<Todo> responseTodos = testUtils.fromJsonFieldAsList(json, "data", Todo.class);

        /* レスポンス検証 */
        // Todoの数が期待値と一致するか確認
        assertThat(responseTodos).hasSameSizeAs(expectedTodos);
        // 内容が期待値と一致するか確認
        assertThat(responseTodos)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedTodos);

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }

    /**
     * 特定のProjectに関する全てのTodoを取得するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void getAllTodosByProject() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 期待値となるProjectの全てのTodoを取得
        List<Todo> expectedProjectTodos = todoRepository.findAllByUserIdAndProjectId(userId, projectId);

        // API実行
        String json = mockMvc
                .perform(get(apiBasePath + queryParamProjectId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        List<Todo> responseTodos = testUtils.fromJsonFieldAsList(json, "data", Todo.class);

        /* レスポンス検証 */
        // Todoの数が期待値と一致するか確認
        assertThat(responseTodos).hasSameSizeAs(expectedProjectTodos);
        // 内容が期待値と一致するか確認
        assertThat(responseTodos)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedProjectTodos);

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }

    /**
     * 特定のLabelに関する全てのTodoを取得するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void getAllTodosByLabel() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 期待値となるProjectの全てのTodoを取得
        List<Todo> expectedLabelTodos = todoRepository.findAllByUserIdAndLabelId(userId, labelId);

        // API実行
        String json = mockMvc
                .perform(get(apiBasePath + queryParamLabelId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        List<Todo> responseTodos = testUtils.fromJsonFieldAsList(json, "data", Todo.class);

        /* レスポンス検証 */
        // Todoの数が期待値と一致するか確認
        assertThat(responseTodos).hasSameSizeAs(expectedLabelTodos);
        // 内容が期待値と一致するか確認
        assertThat(responseTodos)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedLabelTodos);

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }

    /**
     * Todoを作成するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void createTodo() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // Todo作成用フォームを作成
        CreateTodoForm createTodoForm = new CreateTodoForm("createTodo", "createDesc", 1, LocalDate.now(),
                LocalTime.now(),
                Set.of(1L, 2L));
        // フォームをJsonに変換
        String createTodoFormJson = testUtils.toJson(createTodoForm);

        // API実行
        String json = mockMvc
                .perform(post(apiBasePath)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createTodoFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Todo responseCreatedTodo = testUtils.fromJsonField(json, "data", Todo.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseCreatedTodo.getId()).isNotNull();
        assertThat(responseCreatedTodo.getProjectId()).isNull();
        assertThat(responseCreatedTodo.getName()).isEqualTo(createTodoForm.getName());
        assertThat(responseCreatedTodo.getDescription()).isEqualTo(createTodoForm.getDescription());
        assertThat(responseCreatedTodo.getCreatedAt()).isNotNull();
        assertThat(responseCreatedTodo.getUpdatedAt()).isNotNull();

        /* DB検証 */
        // Todoのレコード数が1件増えていることを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi + 1);
    }

    /**
     * 特定のProjectに関連するTodoを作成するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void createTodoWithProject() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // Todo作成用フォームを作成
        CreateTodoForm createTodoForm = new CreateTodoForm("createTodo", "createDesc", 1, LocalDate.now(),
                LocalTime.now(),
                Set.of(1L, 2L));
        // フォームをJsonに変換
        String createTodoFormJson = testUtils.toJson(createTodoForm);

        // API実行
        String json = mockMvc
                .perform(post(apiBasePath + queryParamProjectId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createTodoFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Todo responseCreatedTodo = testUtils.fromJsonField(json, "data", Todo.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseCreatedTodo.getId()).isNotNull();
        assertThat(responseCreatedTodo.getProjectId()).isEqualTo(projectId);
        assertThat(responseCreatedTodo.getName()).isEqualTo(createTodoForm.getName());
        assertThat(responseCreatedTodo.getDescription()).isEqualTo(createTodoForm.getDescription());
        assertThat(responseCreatedTodo.getCreatedAt()).isNotNull();
        assertThat(responseCreatedTodo.getUpdatedAt()).isNotNull();

        /* DB検証 */
        // Todoのレコード数が1件増えていることを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi + 1);
    }

    /**
     * Todoを更新するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void updateTodo() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 更新対象のTodoId
        Long updateTodoId = 1L;
        // Todo作成用フォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("updateTodo", "updateDesc", 1, LocalDate.now(),
                LocalTime.now(), Set.of(1L, 2L));
        // フォームをJsonに変換
        String updateTodoFormJson = testUtils.toJson(updateTodoForm);

        // API実行
        String json = mockMvc
                .perform(patch(apiBasePath + "/" + updateTodoId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTodoFormJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Todo responseUpdatedTodo = testUtils.fromJsonField(json, "data", Todo.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseUpdatedTodo.getId()).isNotNull();
        assertThat(responseUpdatedTodo.getProjectId()).isEqualTo(projectId);
        assertThat(responseUpdatedTodo.getName()).isEqualTo(updateTodoForm.getName());
        assertThat(responseUpdatedTodo.getDescription()).isEqualTo(updateTodoForm.getDescription());
        assertThat(responseUpdatedTodo.getCreatedAt()).isNotNull();
        assertThat(responseUpdatedTodo.getUpdatedAt()).isNotNull();
        // 更新日時が作成日時よりも後か確認
        assertThat(responseUpdatedTodo.getCreatedAt()).isBeforeOrEqualTo(responseUpdatedTodo.getUpdatedAt());

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }

    /**
     * Todoを削除するテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void deleteTodo() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 削除対象のTodoId
        Long deleteTodoId = 1L;

        // API実行
        String json = mockMvc
                .perform(delete(apiBasePath + "/" + deleteTodoId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Long responseDeletedTodoId = testUtils.fromJsonField(json, "data", Long.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseDeletedTodoId).isEqualTo(deleteTodoId);

        /* DB検証 */
        // 削除対象のレコードが見つからないことを確認
        assertTrue(todoRepository.findById(responseDeletedTodoId).isEmpty());
        // Todoのレコード数が1件分減っていることを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi - 1);
    }

    /**
     * Todoを完了状態にするテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void completeTodo() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 更新対象のTodoId
        Long completeTodoId = 1L;
        // 期待値となるProjectのTodoを取得
        Todo expectedTodo = todoRepository.findById(completeTodoId).get();

        // API実行
        String json = mockMvc
                .perform(patch(apiBasePath + "/" + completeTodoId + "/complete")
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Todo responseCompletedTodo = testUtils.fromJsonField(json, "data", Todo.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseCompletedTodo)
                .usingRecursiveComparison()
                .ignoringFields("updatedAt", "isCompleted")
                .ignoringCollectionOrder()
                .isEqualTo(expectedTodo);
        // 完了状態になっているか確認
        assertTrue(responseCompletedTodo.getIsCompleted());
        // 更新日時が作成日時よりも後か確認
        assertThat(responseCompletedTodo.getCreatedAt()).isBeforeOrEqualTo(responseCompletedTodo.getUpdatedAt());

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }

    /**
     * Todoを未完了状態にするテスト。
     * レスポンスとDBの内容が正しいかを確認する。
     */
    @Test
    void incompleteTodo() throws Exception {
        // API実行前の全てのTodoを取得
        long todosCountBeforeApi = todoRepository.count();

        // 更新対象のTodoId
        Long incompleteTodoId = 1L;
        // 期待値となるProjectのTodoを取得
        Todo expectedTodo = todoRepository.findById(incompleteTodoId).get();

        // API実行
        String json = mockMvc
                .perform(patch(apiBasePath + "/" + incompleteTodoId + "/incomplete")
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        Todo responseIncompletedTodo = testUtils.fromJsonField(json, "data", Todo.class);

        /* レスポンス検証 */
        // 内容が期待値と一致するか確認
        assertThat(responseIncompletedTodo)
                .usingRecursiveComparison()
                .ignoringFields("updatedAt", "isCompleted")
                .ignoringCollectionOrder()
                .isEqualTo(expectedTodo);
        // 未完了状態になっているか確認
        assertFalse(responseIncompletedTodo.getIsCompleted());
        // 更新日時が作成日時よりも後か確認
        assertThat(responseIncompletedTodo.getCreatedAt()).isBeforeOrEqualTo(responseIncompletedTodo.getUpdatedAt());

        /* DB検証 */
        // Todoのレコード数が変わっていないことを確認
        long todosCountAfterApi = todoRepository.count();
        assertThat(todosCountAfterApi).isEqualTo(todosCountBeforeApi);
    }
}
