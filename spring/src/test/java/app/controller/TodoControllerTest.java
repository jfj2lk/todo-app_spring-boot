package app.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
import com.jayway.jsonpath.JsonPath;
import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;
import app.seeder.TestLabelSeeder;
import app.seeder.TestTodoSeeder;
import app.seeder.TestUserSeeder;
import app.utils.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils testUtils;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestUserSeeder testUserSeeder;
    @Autowired
    private TestLabelSeeder testLabelSeeder;
    @Autowired
    private TestTodoSeeder testTodoSeeder;

    // 操作を行うユーザーのID
    private Long operatorId = 1L;
    private String jwt;

    @BeforeEach
    void setUpEach() {
        this.jwt = testUtils.createJwt(operatorId);
        // 初期データを作成
        this.testUserSeeder.seedInitialUser();
        this.testLabelSeeder.seedInitialLabel();
        this.testTodoSeeder.seedInitialTodo();
    }

    /**
     * 全てのTodoを取得するテスト。
     * ユーザーに紐づく全件のTodoが取得できている事と、レスポンスの形式が正しいかを確認する。
     */
    @Test
    void getAllTodos() throws Exception {
        // ユーザーに紐づく全てのTodoを取得
        List<Todo> getAllTodosForUser = testUtils.toList(todoRepository.findAllByUserId(1L));
        // 検証用のユーザーに紐づく全てのTodoの数を取得
        long expectedTotalTodoCountForUser = getAllTodosForUser.stream().count();
        // 検証用のTodoを取得
        Todo expectedTodo = getAllTodosForUser.get(0);

        // ユーザーに紐づく全てのTodoを取得
        String json = mockMvc
                .perform(get("/api/todos")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // ユーザーに紐づくTodoが全件取得できているか確認
                .andExpect(jsonPath("$.data.length()").value(expectedTotalTodoCountForUser))
                .andReturn().getResponse().getContentAsString();

        // Jsonから最初のTodoを取得
        Todo actualTodo = testUtils.fieldIndexFromJson(json, "data", 0, Todo.class);

        // レスポンスのTodoの形式が正しいか確認
        assertThat(actualTodo).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "dueTime")
                .withEqualsForType(Object::equals, Set.class)
                .isEqualTo(expectedTodo);
        assertThat(actualTodo)
                .extracting(Todo::getCreatedAt, Todo::getUpdatedAt, Todo::getDueTime)
                .doesNotContainNull();
    }

    @Test
    void Todoを追加() throws Exception {
        // 追加するTodoのID
        Long addTodoId = this.testTodoSeeder.getTodos().size() + 1L;
        // Todoに関連付けるLabelのID
        Set<Long> associatedLabelIds = Set.of(1L, 2L);
        // Todo追加後の全てのTodoの数
        int expectedTotalTodoCount = this.testTodoSeeder.getTodos().size() + 1;
        // Todo追加用のフォームを作成
        AddTodoForm addTodoForm = new AddTodoForm("name3", "desc3", 1, LocalDate.now(), LocalTime.now(),
                associatedLabelIds);
        // Todo追加用のフォームのJSON形式を作成
        String addTodoFormJson = this.testUtils.toJson(addTodoForm);

        // Todo追加
        String json = mockMvc
                .perform(post("/api/todos")
                        .header("Authorization", "Bearer " + this.jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addTodoFormJson))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの追加したTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(addTodoId),
                        jsonPath("$.data.userId").value(this.operatorId),
                        jsonPath("$.data.isCompleted").value("false"),
                        jsonPath("$.data.name").value(addTodoForm.getName()),
                        jsonPath("$.data.desc").value(addTodoForm.getDesc()),
                        jsonPath("$.data.priority").value(addTodoForm.getPriority()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists())
                .andReturn().getResponse().getContentAsString();

        // JsonからTodoを取得
        Todo actualTodo = testUtils.fieldFromJson(json, "data", Todo.class);
        // 期待するTodoを取得
        Todo expectedTodo = todoRepository.findById(addTodoId).get();

        assertThat(actualTodo).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "dueTime")
                .withEqualsForType(Object::equals, Set.class)
                .isEqualTo(expectedTodo);
        assertThat(actualTodo)
                .extracting(Todo::getCreatedAt, Todo::getUpdatedAt, Todo::getDueTime)
                .doesNotContainNull();

    }

    @Test
    void Todoを更新_成功() throws Exception {
        // 更新するTodoのID
        long updateTodoId = 1L;
        // Todoに関連付けるLabelのID
        Set<Long> associatedLabelIds = Set.of(3L, 4L);
        // Todo更新後の全てのTodoの数
        int expectedTotalTodoCount = this.testTodoSeeder.getTodos().size();
        // Todo更新用のフォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("name1update", "desc1update", 4, LocalDate.now(),
                LocalTime.now(), associatedLabelIds);
        // Todo更新用のフォームのJSON形式
        String updateTodoFormJson = this.testUtils.toJson(updateTodoForm);

        // Todoの作成日時と更新日時に差を付ける為、待機する
        Thread.sleep(1000);

        // Todo更新
        mockMvc
                .perform(patch("/api/todos/" + updateTodoId)
                        .header("Authorization", "Bearer " + this.jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTodoFormJson))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの更新したTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(updateTodoId),
                        jsonPath("$.data.userId").value(this.operatorId),
                        jsonPath("$.data.name").value(updateTodoForm.getName()),
                        jsonPath("$.data.desc").value(updateTodoForm.getDesc()),
                        jsonPath("$.data.priority").value(updateTodoForm.getPriority()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists(),
                        // 更新日時が作成日時よりも後になっているか
                        result -> {
                            String responseBody = result.getResponse().getContentAsString();
                            LocalDateTime createdAt = LocalDateTime
                                    .parse(JsonPath.read(responseBody,
                                            "$.data.createdAt"));
                            LocalDateTime updatedAt = LocalDateTime
                                    .parse(JsonPath.read(responseBody,
                                            "$.data.updatedAt"));
                            assertTrue(updatedAt.isAfter(createdAt));
                        });

        // 全てのTodoの件数を取得
        long actualTotalTodoCount = this.todoRepository.count();
        assertEquals(expectedTotalTodoCount, actualTotalTodoCount, "Todoの件数が変わっていないことを確認");

        // TodoとLabelの関連付けが保存されているか確認
        // List<Long> todoLabels =
        // todoLabelRepository.findAllByTodoId(updateTodoId).stream()
        // .map(TodoLabel::getLabelId)
        // .toList();
        // assertTrue(todoLabels.containsAll(associatedLabelIds));
    }

    @Test
    void Todoを更新_失敗() throws Exception {
        // 更新するTodoのID
        long updateTodoId = 3L;
        // Todo更新用のフォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("name1update", "desc1update", 4, LocalDate.now(),
                LocalTime.now(), Set.of(3L, 4L));
        // Todo更新用のフォームのJSON形式
        String updateTodoFormJson = this.testUtils.toJson(updateTodoForm);

        // Todoの作成日時と更新日時に差を付ける為、待機する
        Thread.sleep(1000);

        // Todo更新
        mockMvc
                .perform(patch("/api/todos/" + updateTodoId)
                        .header("Authorization", "Bearer " + this.jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTodoFormJson))
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("更新対象のTodoが見つかりませんでした。"));
    }

    @Test
    void Todoを削除_成功() throws Exception {
        // 削除するTodoのID
        long deleteTodoId = 1L;
        // Todo削除後のTodoの全件数
        int expectedTotalTodoCount = this.testTodoSeeder.getTodos().size() - 1;

        // Todo削除
        mockMvc
                .perform(delete("/api/todos/" + deleteTodoId)
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの形式が正しいか（削除したTodoのIDが返ってくるか）
                .andExpect(jsonPath("$.data").value(deleteTodoId));

        // 全てのTodoの件数を取得
        long actualTotalTodoCount = todoRepository.count();
        assertEquals(expectedTotalTodoCount, actualTotalTodoCount, "Todoが1件分削除されていることを確認");

        // 削除したTodoを取得
        Optional<Todo> deleteTodo = todoRepository.findById(deleteTodoId);
        assertFalse(deleteTodo.isPresent(), "削除したTodoが存在しないことを確認");
    }

    @Test
    void Todoを削除_失敗() throws Exception {
        // 削除するTodoのID
        long deleteTodoId = 3L;

        // Todo削除
        mockMvc
                .perform(delete("/api/todos/" + deleteTodoId)
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("削除対象のTodoが見つかりませんでした。"));
    }

    @Test
    void Todoを完了状態にする() throws Exception {
        // 完了状態にするTodoのID
        int completeTodoId = 2;
        Todo expectedTodo = this.testTodoSeeder.getTodos().get(completeTodoId - 1);

        // Todo完了
        mockMvc
                .perform(patch("/api/todos/" + completeTodoId + "/toggleComplete")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの完了状態にしたTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(expectedTodo.getId()),
                        jsonPath("$.data.userId").value(expectedTodo.getUserId()),
                        jsonPath("$.data.isCompleted").value("true"),
                        jsonPath("$.data.name").value(expectedTodo.getName()),
                        jsonPath("$.data.desc").value(expectedTodo.getDesc()),
                        jsonPath("$.data.priority").value(expectedTodo.getPriority()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists());
    }

    @Test
    void Todoを未完了状態にする() throws Exception {
        // 完了状態にするTodoのID
        int completeTodoId = 1;
        Todo expectedTodo = this.testTodoSeeder.getTodos().get(completeTodoId - 1);

        // Todo完了
        mockMvc
                .perform(patch("/api/todos/" + completeTodoId + "/toggleComplete")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの完了状態にしたTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(expectedTodo.getId()),
                        jsonPath("$.data.userId").value(expectedTodo.getUserId()),
                        jsonPath("$.data.isCompleted").value("false"),
                        jsonPath("$.data.name").value(expectedTodo.getName()),
                        jsonPath("$.data.desc").value(expectedTodo.getDesc()),
                        jsonPath("$.data.priority").value(expectedTodo.getPriority()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists());
    }
}
