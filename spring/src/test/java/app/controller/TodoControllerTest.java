package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.jayway.jsonpath.JsonPath;
import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;
import app.seeder.TestTodoSeeder;
import app.seeder.TestUserSeeder;
import app.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TodoControllerTest {
    private final MockMvc mockMvc;
    private final TodoRepository todoRepository;
    private final TestUtils testUtils;
    private final TestTodoSeeder testTodoSeeder;
    private final TestUserSeeder testUserSeeder;
    // Todo操作を行うユーザーのID
    private final Long operatorForUserId1 = 1L;
    private final String jwtForUserId1;

    @Autowired
    TodoControllerTest(MockMvc mockMvc, TodoRepository todoRepository,
            TestUtils testUtils, TestTodoSeeder testTodoSeeder,
            TestUserSeeder testUserSeeder) {
        this.mockMvc = mockMvc;
        this.todoRepository = todoRepository;
        this.testUtils = testUtils;
        this.testTodoSeeder = testTodoSeeder;
        this.testUserSeeder = testUserSeeder;
        this.jwtForUserId1 = this.testUtils.createJwt(this.operatorForUserId1);
    }

    @BeforeAll
    void setUpAll() {
        // 初期データを作成
        this.testUserSeeder.seedInitialUser();
        this.testTodoSeeder.seedInitialTodo();
    }

    @Test
    void 全てのTodoを取得() throws Exception {
        // ユーザーID1に紐づく全てのTodoの数
        int expectedTotalTodoCount = this.testTodoSeeder.getSeedTodos().stream()
                .filter(todo -> todo.getUserId().equals(this.operatorForUserId1))
                .toList().size();
        // TodoID1の作成予定のTodo情報取得
        Todo expectedTodo = this.testTodoSeeder.getSeedTodos().get(0);

        // ユーザーID1に紐づく全てのTodoを取得
        mockMvc
                .perform(get("/api/todos")
                        .header("Authorization", "Bearer " + this.jwtForUserId1))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // Todoが全件数取得できているか
                .andExpect(jsonPath("$.data.length()").value(expectedTotalTodoCount))
                // レスポンスのTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data[0].id").value(expectedTodo.getId()),
                        jsonPath("$.data[0].userId").value(expectedTodo.getUserId()),
                        jsonPath("$.data[0].isCompleted").value(expectedTodo.getIsCompleted()),
                        jsonPath("$.data[0].name").value(expectedTodo.getName()),
                        jsonPath("$.data[0].desc").value(expectedTodo.getDesc()),
                        jsonPath("$.data[0].priority").value(expectedTodo.getPriority()),
                        jsonPath("$.data[0].createdAt").exists(),
                        jsonPath("$.data[0].updatedAt").exists());
    }

    @Test
    void Todoを追加() throws Exception {
        // 追加するTodoのID
        long addTodoId = this.testTodoSeeder.getSeedTodos().size() + 1;
        // Todo追加後の全てのTodoの数
        int expectedTotalTodoCount = this.testTodoSeeder.getSeedTodos().size() + 1;
        // Todo追加用のフォームを作成
        AddTodoForm addTodoForm = new AddTodoForm("name3", "desc3", 1);
        // Todo追加用のフォームのJSON形式を作成
        String addTodoFormJson = this.testUtils.toJson(addTodoForm);

        // Todo追加
        mockMvc
                .perform(post("/api/todos")
                        .header("Authorization", "Bearer " + this.jwtForUserId1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addTodoFormJson))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの追加したTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(addTodoId),
                        jsonPath("$.data.userId").value(this.operatorForUserId1),
                        jsonPath("$.data.isCompleted").value("false"),
                        jsonPath("$.data.name").value(addTodoForm.getName()),
                        jsonPath("$.data.desc").value(addTodoForm.getDesc()),
                        jsonPath("$.data.priority").value(addTodoForm.getPriority()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists());

        // 全てのTodoの件数を取得
        long actualTotalTodoCount = this.todoRepository.count();
        assertEquals(expectedTotalTodoCount, actualTotalTodoCount, "Todoが1件分追加されていることを確認");
    }

    @Test
    void Todoを更新_成功() throws Exception {
        // 更新するTodoのID
        long updateTodoId = 1L;
        // Todo更新後の全てのTodoの数
        int expectedTotalTodoCount = this.testTodoSeeder.getSeedTodos().size();
        // Todo更新用のフォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("name1update", "desc1update", 4);
        // Todo更新用のフォームのJSON形式
        String updateTodoFormJson = this.testUtils.toJson(updateTodoForm);

        // Todoの作成日時と更新日時に差を付ける為、待機する
        Thread.sleep(1000);

        // Todo更新
        mockMvc
                .perform(patch("/api/todos/" + updateTodoId)
                        .header("Authorization", "Bearer " + this.jwtForUserId1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTodoFormJson))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスの更新したTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(updateTodoId),
                        jsonPath("$.data.userId").value(this.operatorForUserId1),
                        jsonPath("$.data.name").value(updateTodoForm.getName()),
                        jsonPath("$.data.desc").value(updateTodoForm.getDesc()),
                        jsonPath("$.data.priority").value(updateTodoForm.getPriority()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists(),
                        // 更新日時が作成日時よりも後になっているか
                        result -> {
                            String responseBody = result.getResponse().getContentAsString();
                            OffsetDateTime createdAt = OffsetDateTime
                                    .parse(JsonPath.read(responseBody,
                                            "$.data.createdAt"));
                            OffsetDateTime updatedAt = OffsetDateTime
                                    .parse(JsonPath.read(responseBody,
                                            "$.data.updatedAt"));
                            assertTrue(updatedAt.isAfter(createdAt));
                        });

        // 全てのTodoの件数を取得
        long actualTotalTodoCount = this.todoRepository.count();
        assertEquals(expectedTotalTodoCount, actualTotalTodoCount, "Todoの件数が変わっていないことを確認");
    }

    @Test
    void Todoを更新_失敗() throws Exception {
        // 更新するTodoのID
        long updateTodoId = 3L;
        // Todo更新用のフォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("name1update", "desc1update", 4);
        // Todo更新用のフォームのJSON形式
        String updateTodoFormJson = this.testUtils.toJson(updateTodoForm);

        // Todoの作成日時と更新日時に差を付ける為、待機する
        Thread.sleep(1000);

        // Todo更新
        mockMvc
                .perform(patch("/api/todos/" + updateTodoId)
                        .header("Authorization", "Bearer " + this.jwtForUserId1)
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
        int expectedTotalTodoCount = this.testTodoSeeder.getSeedTodos().size() - 1;

        // Todo削除
        mockMvc
                .perform(delete("/api/todos/" + deleteTodoId)
                        .header("Authorization", "Bearer " + this.jwtForUserId1))
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
                        .header("Authorization", "Bearer " + this.jwtForUserId1))
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
        Todo expectedTodo = this.testTodoSeeder.getSeedTodos().get(completeTodoId - 1);

        // Todo完了
        mockMvc
                .perform(patch("/api/todos/" + completeTodoId + "/toggleComplete")
                        .header("Authorization", "Bearer " + this.jwtForUserId1))
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
        Todo expectedTodo = this.testTodoSeeder.getSeedTodos().get(completeTodoId - 1);

        // Todo完了
        mockMvc
                .perform(patch("/api/todos/" + completeTodoId + "/toggleComplete")
                        .header("Authorization", "Bearer " + this.jwtForUserId1))
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
