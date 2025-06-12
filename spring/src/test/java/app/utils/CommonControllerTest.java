package app.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CommonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtils testUtils;

    private String jwt;
    private Long userId;

    public CommonControllerTest() {
        jwt = testUtils.createJwt(userId);
    }

    /**
     * 全てのリソースを取得するAPIのテスト
     */
    public <T> void getAllResources(
            String endpoint,
            Supplier<List<T>> findAll,
            Function<Long, List<T>> findByReferenceId,
            Class<T> classType) throws Exception {

        // API実行前の全てのエンティティを取得
        List<T> entitiesCountBeforeApi = findAll.get();

        // 期待値となるUserの全てのエンティティを取得
        List<T> expectedUserEntities = findByReferenceId.apply(userId);

        // API実行
        String json = mockMvc
                .perform(get("/api/" + endpoint)
                        .header("Authorization", "Bearer " + jwt))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // Jsonをオブジェクトに変換
        List<T> responseUserEntities = testUtils.fromJsonFieldAsList(json, "data", classType);

        /* レスポンス検証 */
        // エンティティの数が期待値と一致するか確認
        assertThat(responseUserEntities).hasSameSizeAs(expectedUserEntities);
        // 内容が期待値と一致するか確認
        assertThat(responseUserEntities)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserEntities);

        /* DB検証 */
        // エンティティのレコード数が変わっていないことを確認
        List<T> entitiesCountAfterApi = findAll.get();
        assertThat(entitiesCountAfterApi).hasSize(entitiesCountBeforeApi.size());
    }
}
