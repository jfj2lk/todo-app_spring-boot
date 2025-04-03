package app.aspect.archive;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 未処理の例外処理
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
    }
}
