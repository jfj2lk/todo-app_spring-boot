package app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckTodoExistsValidator.class)
public @interface CheckTodoExists {

    String message() default "指定されたTodoが見つかりません";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
