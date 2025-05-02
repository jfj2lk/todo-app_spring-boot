package app.auth;

import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtInfo jwtInfo;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // リクエストURLが許可リストに入っているかの判定結果を取得
        boolean includedAllowList = isIncludedAllowList(request);
        if (includedAllowList) {
            // 許可リストに入っている場合は次のフィルターを実行する
            doFilter(request, response, filterChain);
            return;
        }


        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // Authorizationヘッダーがないか、形式が無効な場合は次のフィルターに進む
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer "の後のトークン部分を抽出
        jwt = authHeader.substring(7);

        // JWT検証
        try {
            jwtService.validateJwt(jwt);
        } catch (ExpiredJwtException e) {
            // JWTの有効期限切れの場合
            createResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "JWTの有効期限が切れています。");
            return;
        } catch (Exception e) {
            // JWTの検証中にエラーが発生した場合
            createResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT検証中にエラーが発生しました。");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * リクエストURLが許可リストのURLに含まれているか
     */
    public boolean isIncludedAllowList(HttpServletRequest request) {
        // リクエストURIを取得
        String requestUri = request.getRequestURI();
        // リクエストURIが許可リストのURLに含まれているかの判定結果を返す
        return Arrays.asList(jwtInfo.permitAllUrls)
                .stream().anyMatch(requestUri::startsWith);
    }

    /**
     * レスポンスを作成する。
     */
    private void createResponse(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String json = "{\"message\": \"" + message + "\"}";
        response.getWriter().write(json);
    }
}
