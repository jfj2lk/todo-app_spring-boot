package app.aspect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * カスタムアノテーションのエラーレスポンスの設定
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Object> handleValidationException(HandlerMethodValidationException e) {
        // バリデーションエラーメッセージを取得
        List<Map<String, String>> errors = e.getAllErrors().stream()
                .map(error -> {
                    Map<String, String> errorDetails = new HashMap<>();
                    errorDetails.put("defaultMessage", error.getDefaultMessage());
                    return errorDetails;
                })
                .toList();
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    /**
     * 
     * 未処理の例外処理
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        return ResponseEntity.internalServerError().body(Map.of("message",
                e.getMessage()));
    }
}
