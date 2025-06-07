package app.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import app.model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestUtils {
    private final JwtUtils jwtUtils;
    private final PasswordUtils passwordUtils;
    private ObjectMapper mapper = createMapper();

    /**
     * ObjectMapperを作成する。
     */
    public ObjectMapper createMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    /**
     * 指定されたIDのユーザーのJWTを作成する。
     */
    public String createJwt(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(userId, "user1", "email1", passwordUtils.encode("password1"), now, now);
        return jwtUtils.generateJwt(user);
    }

    /**
     * オブジェクトをJSON文字列に変換する。
     */
    public String toJson(Object object) throws Exception {
        return mapper.writeValueAsString(object);
    }

    /**
     * JSON文字列を指定のオブジェクトに変換する。
     */
    public <T> T fromJson(String jsonString, Class<T> classType) throws Exception {
        return mapper.readValue(jsonString, classType);
    }

    /**
     * JSON文字列から、指定されたフィールドの値を抽出する。
     */
    public JsonNode extractFieldFromJson(String jsonString, String fieldName) throws Exception {
        return mapper.readTree(jsonString).get(fieldName);
    }

    /**
     * JsonNodeを指定のオブジェクトに変換する。
     */
    public <T> T fromJsonNode(JsonNode jsonNode, Class<T> classType) throws Exception {
        return mapper.treeToValue(jsonNode, classType);
    }

    /**
     * JSON文字列から指定されたフィールドの値を抽出し、指定のオブジェクトに変換する。
     */
    public <T> T fieldFromJson(String jsonString, String fieldName, Class<T> classType) throws Exception {
        JsonNode jsonNode = extractFieldFromJson(jsonString, fieldName);
        return fromJsonNode(jsonNode, classType);
    }

    /**
     * JSON文字列から指定されたフィールドの指定されたインデックスの値を抽出し、指定のオブジェクトに変換する。
     */
    public <T> T fieldIndexFromJson(String jsonString, String fieldName, int index, Class<T> classType)
            throws Exception {
        JsonNode jsonNode = extractFieldFromJson(jsonString, fieldName).get(index);
        return fromJsonNode(jsonNode, classType);
    }

    /**
     * IterableをListに変換する。
     */
    public <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
