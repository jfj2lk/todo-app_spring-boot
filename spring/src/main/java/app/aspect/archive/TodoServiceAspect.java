package app.aspect.archive;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import app.repository.TodoRepository;

// @Aspect
// @Component
public class TodoServiceAspect {
    private TodoRepository todoRepository;

    public TodoServiceAspect(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * メソッドの引数にIDがある場合は、そのIDに対応するレコードの存在確認を行い、存在しなければ例外を投げる
     */
    @Around("execution(* app.service..*.*(..)) && args(id, ..)")
    public Object checkDataExist(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        // 指定されたIDに対応するTodoが存在しない場合は例外を投げる
        todoRepository.findById(id).orElseThrow(() -> new Exception(
                "id=" + id + "のTodoが見つかりません"));

        // 元の処理を実行
        return joinPoint.proceed();
    }
}
