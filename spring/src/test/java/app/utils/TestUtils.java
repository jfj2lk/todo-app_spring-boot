package app.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
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
     * 指定されたユーザーIDのJWTを作成する。
     */
    public String createJwt(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(userId, "user1", "email1", passwordUtils.encode("password1"), now, now);
        return jwtUtils.generateJwt(user);
    }

    /**
     * ObjectMapperを作成する。
     */
    public ObjectMapper createMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    /**
     * オブジェクトをJSON文字列に変換する。
     */
    public String toJson(Object object) throws Exception {
        return mapper.writeValueAsString(object);
    }

    /**
     * JSON文字列から指定されたフィールドの値を抽出し、指定のオブジェクトに変換する。
     */
    public <T> T fromJsonField(String jsonString, String fieldName, Class<T> classType) throws Exception {
        JsonNode jsonNode = mapper.readTree(jsonString).get(fieldName);
        return mapper.treeToValue(jsonNode, classType);
    }

    /**
     * JSON文字列から指定されたフィールドの値を抽出し、List型でラップした指定のオブジェクトに変換する。
     */
    public <T> List<T> fromJsonFieldAsList(String jsonString, String fieldName, Class<T> classType) throws Exception {
        JsonNode jsonNode = mapper.readTree(jsonString).get(fieldName);
        CollectionType listType = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, classType);
        return mapper.convertValue(jsonNode, listType);
    }

    /**
     * IterableをListに変換する。
     */
    public <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(
                iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
