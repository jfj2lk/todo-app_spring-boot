package app.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
        List<Todo> allTodosForUser = testUtils.toList(todoRepository.findAllByUserId(1L));
        // 検証用のユーザーに紐づく全てのTodoの数を取得
        long expectedTodoCountForUser = allTodosForUser.size();
        // 検証用のTodoを取得
        Todo expectedTodo = allTodosForUser.get(0);

        // ユーザーに紐づく全てのTodoを取得
        String json = mockMvc
                .perform(get("/api/todos")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // ユーザーに紐づくTodoが全件取得できているか確認
                .andExpect(jsonPath("$.data.length()").value(expectedTodoCountForUser))
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

    /**
     * Todoを追加するテスト。
     * Todo追加後にレコードの件数が1件増えている、レスポンスの形式が正しいかを確認する。
     */
    @Test
    void addTodo() throws Exception {
        // 検証用のTodo追加後のユーザーに紐づく全てのTodoの数を取得
        long expectedTodoCountAfterAdd = todoRepository.count() + 1;
        // Todo追加用のフォームを作成
        AddTodoForm addTodoForm = new AddTodoForm("name3", "desc3", 1, LocalDate.now(), LocalTime.now(),
                Set.of(1L, 2L));
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
                .andReturn().getResponse().getContentAsString();

        // JsonからTodoを取得
        Todo actualTodo = testUtils.fieldFromJson(json, "data", Todo.class);
        // DBから追加後のTodoを取得
        Todo expectedTodo = todoRepository.findById(expectedTodoCountAfterAdd).get();
        // DBから全てのTodoの数を取得
        long actualTodoCountAfterAdd = todoRepository.count();

        // レスポンスのTodoの形式が正しいか確認
        assertThat(actualTodo).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "dueTime")
                .withEqualsForType(Object::equals, Set.class)
                .isEqualTo(expectedTodo);
        assertThat(actualTodo)
                .extracting(Todo::getCreatedAt, Todo::getUpdatedAt, Todo::getDueTime)
                .doesNotContainNull();

        // Todoの件数が1件分増えているか確認
        assertEquals(expectedTodoCountAfterAdd, actualTodoCountAfterAdd);
    }

    /**
     * Todoを更新するテスト。
     * Todo更新後にレコードの件数が変わっていない、レスポンスの形式が正しいかを確認する。
     */
    @Test
    void updateTodo() throws Exception {
        // 更新するTodoのID
        long updateTodoId = 1L;
        // 検証用のTodo更新後のユーザーに紐づく全てのTodoの数を取得
        long expectedTodoCountAfterUpdate = todoRepository.count();
        // Todo追加用のフォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("name3", "desc3", 1, LocalDate.now(), LocalTime.now(),
                Set.of(1L, 2L));
        // Todo更新用のフォームのJSON形式を作成
        String updateTodoFormJson = this.testUtils.toJson(updateTodoForm);

        // Todo追加
        String json = mockMvc
                .perform(patch("/api/todos/" + updateTodoId)
                        .header("Authorization", "Bearer " + this.jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTodoFormJson))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // JsonからTodoを取得
        Todo actualTodo = testUtils.fieldFromJson(json, "data", Todo.class);
        // DBから更新後のTodoを取得
        Todo expectedTodo = todoRepository.findById(updateTodoId).get();
        // DBから全てのTodoの数を取得
        long actualTodoCountAfterUpdate = todoRepository.count();

        // レスポンスのTodoの形式が正しいか確認
        assertThat(actualTodo).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "dueTime")
                .withEqualsForType(Object::equals, Set.class)
                .isEqualTo(expectedTodo);
        assertThat(actualTodo)
                .extracting(Todo::getCreatedAt, Todo::getUpdatedAt, Todo::getDueTime)
                .doesNotContainNull();

        // Todoの件数が変わっていないことを確認
        assertEquals(expectedTodoCountAfterUpdate, actualTodoCountAfterUpdate);
    }

    /**
     * Todoを削除するテスト。
     * Todo削除後にレコードの件数が1件減っている、レスポンスの形式が正しいかを確認する。
     */
    @Test
    void deleteTodo() throws Exception {
        // 削除するTodoのID
        long deleteTodoId = 1L;
        // 検証用のTodo更新後のユーザーに紐づく全てのTodoの数を取得
        long expectedTodoCountAfterDelete = todoRepository.count() - 1;

        // Todo追加
        String json = mockMvc
                .perform(delete("/api/todos/" + deleteTodoId)
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // JsonからTodoを取得
        long actualDeleteTodoId = testUtils.fieldFromJson(json, "data", Long.class);
        // DBから全てのTodoの数を取得
        long actualTodoCountAfterUpdate = todoRepository.count();

        // レスポンスのTodoのIDと削除したTodoのIDが合っているかを確認
        assertEquals(deleteTodoId, actualDeleteTodoId);

        // Todoの件数が1件分減っていることを確認
        assertEquals(expectedTodoCountAfterDelete, actualTodoCountAfterUpdate);
    }

    /**
     * Todoを完了状態にするテスト。
     * レスポンスの形式が正しいかを確認する。
     */
    @Test
    void completeTodo() throws Exception {
        // 完了状態にするTodoのID
        long completeTodoId = 1L;
        // Todo完了後の検証用のTodoを取得
        Todo expectedTodo = todoRepository.findById(completeTodoId).get();
        // 検証用のTodo完了後の全てのTodoの数を取得
        long expectedTodoCountAfterComplete = todoRepository.count();

        // Todo完了
        String json = mockMvc
                .perform(patch("/api/todos/" + completeTodoId + "/toggleComplete")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // JsonからTodoを取得
        Todo actualTodo = testUtils.fieldFromJson(json, "data", Todo.class);
        // DBから全てのTodoの数を取得
        long actualTodoCountAfterComplete = todoRepository.count();

        // レスポンスのTodoの形式が正しいか確認
        assertThat(actualTodo).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "dueTime", "isCompleted")
                .withEqualsForType(Object::equals, Set.class)
                .isEqualTo(expectedTodo);
        assertThat(actualTodo)
                .extracting(Todo::getCreatedAt, Todo::getUpdatedAt, Todo::getDueTime)
                .doesNotContainNull();

        // Todoの状態が完了になっていることを確認
        assertTrue(actualTodo.getIsCompleted());

        // Todoの件数が変わっていないことを確認
        assertEquals(expectedTodoCountAfterComplete, actualTodoCountAfterComplete);
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
