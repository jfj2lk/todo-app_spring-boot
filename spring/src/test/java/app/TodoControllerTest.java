package app;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import app.auth.JwtService;
import app.form.todo.AddTodoForm;
import app.form.todo.UpdateTodoForm;
import app.model.Todo;
import app.model.User;
import app.repository.TodoRepository;
import app.repository.UserRepository;
import app.seeder.TestTodoSeeder;
import app.seeder.TestUserSeeder;
import app.service.TodoService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class TodoControllerTest {
    private final MockMvc mockMvc;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TodoService todoService;
    private final TodoRepository todoRepository;
    private final TestTodoSeeder testTodoSeeder;
    private final TestUserSeeder testUserSeeder;
    private final EntityManager entityManager;
    private String jwt;
    private final TestUtils testUtils;

    @BeforeAll
    public void seedInitialData() {
        testUserSeeder.seedInitialUser();
        testTodoSeeder.seedInitialTodo();
        User user = userRepository.findById(1l).orElseThrow();
        this.jwt = jwtService.generateJwt(user);
    }

    @Test
    void 全てのTodoが取得できるか() throws Exception {
        // IDが1のTodoを取得
        Todo expectedTodo = this.testTodoSeeder.getSeedTodos().get(0);

        // 全てのTodoを取得
        mockMvc
                .perform(get("/api/todos")
                        .header("Authorization", "Bearer " + this.jwt))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                // 全てのTodoが取得できているか
                .andExpect(jsonPath("$.data.length()")
                        .value(this.testTodoSeeder.getSeedTodos().size()))
                // 取得したTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data[0].id").value(expectedTodo.getId()),
                        jsonPath("$.data[0].name").value(expectedTodo.getName()),
                        jsonPath("$.data[0].desc").value(expectedTodo.getDesc()),
                        jsonPath("$.data[0].createdAt").exists(),
                        jsonPath("$.data[0].updatedAt").exists());
    }

    @Test
    void Todoが追加できるか() throws Exception {
        // Todo追加用のフォームを作成
        AddTodoForm addTodoForm = new AddTodoForm("name3", "desc3");
        // Todo追加用のフォームのJSON形式
        String addTodoFormJson = testUtils.toJson(addTodoForm);
        // 追加したTodoのID
        int addedTodoId = this.testTodoSeeder.getSeedTodos().size() + 1;

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
                // 追加したTodoの形式が正しいか
                .andExpectAll(
                        jsonPath("$.data.id").value(addedTodoId),
                        jsonPath("$.data.name").value(addTodoForm.getName()),
                        jsonPath("$.data.desc").value(addTodoForm.getDesc()),
                        jsonPath("$.data.createdAt").exists(),
                        jsonPath("$.data.updatedAt").exists());
    }

    // @Test
    // void Todoが更新できるか() throws InterruptedException {
    // // 更新対象のTodoのID
    // final Long updateTodoId = 1l;
    // // 更新前のTodo取得
    // final Todo beforeUpdateTodo = todoRepository.findById(updateTodoId).get();
    // entityManager.clear();

    // // Todo入力内容の作成
    // final UpdateTodoForm updateTodoInput =
    // new UpdateTodoForm("UpdateTodo", "UpdateDesc");
    // final UpdateTodoForm updateTodoForm = new UpdateTodoForm(updateTodoInput);

    // // Todo更新
    // Thread.sleep(1000);
    // final Todo updatedTodo = todoService.updateTodo(updateTodoId, updateTodoForm);

    // // テスト
    // assertAll("値が更新されていないことを確認",
    // () -> assertEquals(beforeUpdateTodo.getCreatedAt(), updatedTodo.getCreatedAt()),
    // () -> assertEquals(beforeUpdateTodo.getId(), updatedTodo.getId()));
    // assertAll("値が更新されていることを確認",
    // () -> assertEquals("UpdateTodo", updatedTodo.getName()),
    // () -> assertEquals("UpdateDesc", updatedTodo.getDesc()),
    // () -> assertNotEquals(beforeUpdateTodo.getUpdatedAt(), updatedTodo.getUpdatedAt()));
    // }

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
    // final long allTodosCount = StreamSupport.stream(allTodos.spliterator(), false).count();

    // // テスト
    // assertEquals(dbAllTodosCount - 1, allTodosCount, "Todoの件数が1件少なくなっていることを確認");
    // assertEquals(deleteTodoId, deletedTodoId, "削除したTodoのIDが取得できていることを確認");
    // assertFalse(deletedTodo.isPresent(), "削除したTodoが存在しないことを確認");
    // }
}
