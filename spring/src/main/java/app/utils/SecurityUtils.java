package app.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    /**
     * 現在のユーザー情報を取得する。
     */
    public Long getCurrentUserId() {
        return Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
