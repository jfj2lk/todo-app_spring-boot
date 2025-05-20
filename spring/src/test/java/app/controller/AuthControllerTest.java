package app.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.jayway.jsonpath.JsonPath;

import app.form.user.LoginForm;
import app.form.user.SignUpForm;
import app.model.User;
import app.repository.UserRepository;
import app.seeder.TestTodoSeeder;
import app.seeder.TestUserSeeder;
import app.utils.JwtUtils;
import app.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class AuthControllerTest {
        private final MockMvc mockMvc;
        private final UserRepository userRepository;
        private final TestUtils testUtils;
        private final TestTodoSeeder testTodoSeeder;
        private final TestUserSeeder testUserSeeder;
        private final PasswordEncoder passwordEncoder;
        private final JwtUtils jwtService;

        @Autowired
        AuthControllerTest(MockMvc mockMvc, UserRepository userRepository,
                        TestUtils testUtils, TestTodoSeeder testTodoSeeder,
                        TestUserSeeder testUserSeeder, JwtUtils jwtService) {
                this.mockMvc = mockMvc;
                this.userRepository = userRepository;
                this.testUtils = testUtils;
                this.testTodoSeeder = testTodoSeeder;
                this.testUserSeeder = testUserSeeder;
                this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                this.jwtService = jwtService;
        }

        @BeforeAll
        void setUpAll() {
                // 初期データを作成
                this.testUserSeeder.seedInitialUser();
                this.testTodoSeeder.seedInitialTodo();
        }

        @Test
        void 新規登録できるか() throws Exception {
                // 期待する新規登録するユーザーのID
                long expectedSignUpUserId = this.testUserSeeder.getSeedUsers().size() + 1;
                // 期待する新規登録後の全てのユーザーのレコード数
                long expectedTotalUserCount = this.testUserSeeder.getSeedUsers().size() + 1;
                // 新規登録用のフォーム
                SignUpForm signUpForm = new SignUpForm("c", "c@c", "c");
                // 新規登録用のフォームのJSON形式
                String signUpFormJson = this.testUtils.toJson(signUpForm);

                // 新規登録APIのレスポンス検証
                String responseContents = mockMvc
                                .perform(post("/api/auth/signup")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(signUpFormJson))
                                .andDo(print())
                                .andExpectAll(
                                                status().isOk(),
                                                content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("新規登録しました。"))
                                .andReturn().getResponse().getContentAsString();

                // レスポンスのJWTの形式が正しいか確認
                String jwt = JsonPath.read(responseContents, "$.accessToken");
                assertDoesNotThrow(() -> this.jwtService.validateJwt(jwt), "レスポンスのJWTの形式が正しいことを確認");

                // 新規登録したユーザーがDBに追加されているか確認
                Optional<User> signedUpUserOptional = userRepository.findByEmail("c@c");
                assertTrue(signedUpUserOptional.isPresent(), "新規登録したユーザーがDBに追加されていることを確認");
                User signedUpUser = signedUpUserOptional.get();

                // 新規登録したユーザーの形式が正しいか確認
                assertAll(
                                () -> assertEquals(expectedSignUpUserId, signedUpUser.getId()),
                                () -> assertEquals(signUpForm.getName(), signedUpUser.getName()),
                                () -> assertEquals(signUpForm.getEmail(), signedUpUser.getEmail()),
                                () -> assertTrue(this.passwordEncoder.matches(
                                                signUpForm.getPassword(),
                                                signedUpUser.getPassword())),
                                () -> assertNotNull(signedUpUser.getCreatedAt()),
                                () -> assertNotNull(signedUpUser.getUpdatedAt()));

                // 新規登録したユーザー分、レコードの数が増えているか確認
                long totalUserCount = userRepository.count();
                assertEquals(expectedTotalUserCount, totalUserCount, "新規登録したユーザー分、レコードの数が増えていることを確認");
        }

        @Test
        void ログインできるか() throws Exception {
                // 期待するログインするユーザーのID
                long expectedLoginUserId = 1;
                // 期待するログイン後の全てのユーザーのレコード数
                long expectedTotalUserCount = this.testUserSeeder.getSeedUsers().size();
                // ログイン用のフォーム
                LoginForm loginForm = new LoginForm("a@a", "a");
                // ログイン用のフォームのJSON形式
                String loginFormJson = this.testUtils.toJson(loginForm);

                // ログインAPIのレスポンス検証
                String responseContents = mockMvc
                                .perform(post("/api/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(loginFormJson))
                                .andDo(print())
                                .andExpectAll(
                                                status().isOk(),
                                                content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("ログインしました。"))
                                .andReturn().getResponse().getContentAsString();

                // レスポンスのJWTの形式が正しいか確認
                String jwt = JsonPath.read(responseContents, "$.accessToken");
                assertDoesNotThrow(() -> this.jwtService.validateJwt(jwt), "レスポンスのJWTの形式が正しいことを確認");

                // 新規登録したユーザーの形式が正しいか確認
                User loggedinUser = userRepository.findByEmail("a@a").get();
                assertAll(
                                () -> assertEquals(expectedLoginUserId, loggedinUser.getId()),
                                () -> assertEquals(loginForm.getEmail(), loggedinUser.getEmail()),
                                () -> assertTrue(this.passwordEncoder.matches(
                                                loginForm.getPassword(),
                                                loggedinUser.getPassword())),
                                () -> assertNotNull(loggedinUser.getCreatedAt()),
                                () -> assertNotNull(loggedinUser.getUpdatedAt()));
        }
}
