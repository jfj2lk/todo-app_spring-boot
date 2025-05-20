package app.aspect.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * バリデーションエラーの例外処理。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        // バリデーションエラーの整形
        Map<String, List<String>> validationErrors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        // レスポンスメッセージ作成
        String message = "フォームの入力内容にエラーがあります。\n" +
                validationErrors.entrySet().stream()
                        .map(entry -> "・" + entry.getKey() + ": " + String.join("、\n", entry.getValue()))
                        .collect(Collectors.joining("\n"));

        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    /**
     * 未処理の例外をキャッチして、エラーレスポンスを返す。
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUncaughtException(Exception e) {
        return ResponseEntity.internalServerError().body(
                Map.of("message", e.getMessage()));
    }
}
