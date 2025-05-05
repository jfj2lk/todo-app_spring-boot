package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.jayway.jsonpath.JsonPath;
import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.repository.TodoRepository;
import app.seeder.TestTodoSeeder;
import app.seeder.TestUserSeeder;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TodoControllerTest {
    private final MockMvc mockMvc;
    private final TodoRepository todoRepository;
    private final TestUtils testUtils;
    private final TestTodoSeeder testTodoSeeder;
    private final TestUserSeeder testUserSeeder;
    private final String jwt;

    TodoControllerTest(MockMvc mockMvc, TodoRepository todoRepository, TestUtils testUtils,
            TestTodoSeeder testTodoSeeder,
            TestUserSeeder testUserSeeder) {
        this.mockMvc = mockMvc;
        this.todoRepository = todoRepository;
        this.testUtils = testUtils;
        this.testTodoSeeder = testTodoSeeder;
        this.testUserSeeder = testUserSeeder;
        this.jwt = this.testUtils.createJwt();
    }

    @BeforeAll
    void setUpAll() {
        // 初期データを作成
        this.testUserSeeder.seedInitialUser();
        this.testTodoSeeder.seedInitialTodo();
    }

    @Test
    void 全てのTodoが取得できるか() throws Exception {
        // IDが1の作成予定のTodo情報を取得
        Todo expectedTodo = this.testTodoSeeder.getSeedTodos().get(0);

        // 全てのTodoを取得
        mockMvc
                .perform(get("/api/todos")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // Todoが全件数取得できているか
                .andExpect(jsonPath("$.data.length()")
                        .value(this.testTodoSeeder.getSeedTodos().size()))
                // レスポンスのTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data[0].id").value(expectedTodo.getId()),
                        jsonPath("$.data[0].name").value(expectedTodo.getName()),
                        jsonPath("$.data[0].desc").value(expectedTodo.getDesc()),
                        jsonPath("$.data[0].createdAt").exists(),
                        jsonPath("$.data[0].updatedAt").exists());
    }

    @Test
    void Todoが追加できるか() throws Exception {
        // 追加するTodoのID
        long addTodoId = this.testTodoSeeder.getSeedTodos().size() + 1;
        // Todo追加後のTodoの全件数
        int expectedTotalTodoCount = this.testTodoSeeder.getSeedTodos().size() + 1;
        // Todo追加用のフォームを作成
        AddTodoForm addTodoForm = new AddTodoForm("name3", "desc3");
        // Todo追加用のフォームのJSON形式を作成
        String addTodoFormJson = this.testUtils.toJson(addTodoForm);

        // Todo追加
        mockMvc
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
                        jsonPath("$.data.name").value(addTodoForm.getName()),
                        jsonPath("$.data.desc").value(addTodoForm.getDesc()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists());

        // 全てのTodoの件数を取得
        long actualTotalTodoCount = this.todoRepository.count();
        assertEquals(expectedTotalTodoCount, actualTotalTodoCount, "Todoが1件分追加されていることを確認");
    }

    @Test
    void Todoが更新できるか() throws Exception {
        // 更新するTodoのID
        long updateTodoId = 1;
        // Todo更新用のフォームを作成
        UpdateTodoForm updateTodoForm = new UpdateTodoForm("name1update", "desc1update");
        // Todo追加用のフォームのJSON形式
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
                        jsonPath("$.data.name").value(updateTodoForm.getName()),
                        jsonPath("$.data.desc").value(updateTodoForm.getDesc()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists(),
                        // 更新日時が作成日時よりも後になっているか
                        result -> {
                            String responseBody = result.getResponse().getContentAsString();
                            OffsetDateTime createdAt = OffsetDateTime
                                    .parse(JsonPath.read(responseBody, "$.data.createdAt"));
                            OffsetDateTime updatedAt = OffsetDateTime
                                    .parse(JsonPath.read(responseBody, "$.data.updatedAt"));
                            assertTrue(updatedAt.isAfter(createdAt));
                        });
    }

    // @Test
    // void Todoが削除できるか() {
    // // 全てのTodoの数
    // final int dbAllTodosCount = 2;
    // // 削除対象のTodoのID
    // final Long deleteTodoId = 1l;

    // // Todo削除
    // final Long deletedTodoId = todoService.deleteTodo(deleteTodoId);
    // // 削除したTodoを取得
    // final Optional<Todo> deletedTodo = todoRepository.findById(deleteTodoId);

    // // 全てのTodo取得
    // final Iterable<Todo> allTodos = todoService.getAllTodos();
    // // 取得したTodoの数を取得
    // final long allTodosCount = StreamSupport.stream(allTodos.spliterator(),
    // false).count();

    // // テスト
    // assertEquals(dbAllTodosCount - 1, allTodosCount, "Todoの件数が1件少なくなっていることを確認");
    // assertEquals(deleteTodoId, deletedTodoId, "削除したTodoのIDが取得できていることを確認");
    // assertFalse(deletedTodo.isPresent(), "削除したTodoが存在しないことを確認");
    // }
}
