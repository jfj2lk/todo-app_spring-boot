package app.annotation;

import org.springframework.stereotype.Component;

import app.repository.TodoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CheckTodoExistsValidator implements ConstraintValidator<CheckTodoExists, Long> {

    private final TodoRepository todoRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null || !todoRepository.existsById(id)) {
            // デフォルトのメッセージを無効化
            context.disableDefaultConstraintViolation();
            // カスタムメッセージを設定
            context.buildConstraintViolationWithTemplate("id=" + id + " のTodoが見つかりません")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
