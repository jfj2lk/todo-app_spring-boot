package app.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import app.repository.UserRepository;
import app.seeder.TestTodoSeeder;
import app.seeder.TestUserSeeder;
import app.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class AuthControllerTest {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final TestUtils testUtils;
    private final TestTodoSeeder testTodoSeeder;
    private final TestUserSeeder testUserSeeder;
    private final String jwt;

    AuthControllerTest(MockMvc mockMvc, UserRepository userRepository, TestUtils testUtils,
            TestTodoSeeder testTodoSeeder,
            TestUserSeeder testUserSeeder) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
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

}
