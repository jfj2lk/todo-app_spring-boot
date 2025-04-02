package app.aspect;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ControllerAspect {

    /**
     * コントローラーの引数にBindingResultがある場合は、バリデーションチェックを行い、バリデーションエラーが発生している場合はエラーレスポンスを返す
     */
    @Around("execution(* app.controller..*.*(..))")
    public Object validateRequestBody(ProceedingJoinPoint joinPoint) throws Throwable {
        // メソッドに渡される引数を取得
        Object[] args = joinPoint.getArgs();

        BindingResult bindingResult = null;

        // 指定の引数が無いかチェック
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                // BindingResultが引数に存在する場合、それを変数に格納
                bindingResult = (BindingResult) arg;
            }
        }

        // バリデーションエラーがある場合、エラーレスポンスを返す
        if (bindingResult != null && bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("validationErrorMessages", bindingResult.getAllErrors()));
        }

        // 元の処理を続行
        return joinPoint.proceed();
    }
}
