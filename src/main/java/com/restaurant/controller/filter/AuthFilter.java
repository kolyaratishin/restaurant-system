package com.restaurant.controller.filter;

import com.restaurant.controller.filter.auth.Handler;
import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;

@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final Handler handler;
    private final Set<String> URIsToFilter = Set.of("/api/restaurant/",
            "/api/export",
            "/api/import",
            "/api/meal",
            "/api/group",
            "/api/receipt",
            "/api/table",
            "/api/statistics/");

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        BodyHttpServletRequestWrapper requestWrapper = new BodyHttpServletRequestWrapper(request);

        URIsToFilter.stream()
                .filter(uri -> requestWrapper.getRequestURI().startsWith(uri))
                .findFirst()
                .ifPresentOrElse((uri) -> {
                    if (checkWhetherUserHasAccess(requestWrapper)) {
                        doFilter(requestWrapper, response, filterChain);
                    } else {
                        sendError(response);
                    }
                }, () -> doFilter(requestWrapper, response, filterChain));
    }

    private void sendError(HttpServletResponse response) {
        try {
            response.setHeader("WWW-Authenticate", "Basic realm=\"Access to the resource is restricted\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkWhetherUserHasAccess(BodyHttpServletRequestWrapper request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // розкодувати ім'я користувача та пароль з заголовку Authorization
            String[] authTokens = new String(Base64.getDecoder().decode(authHeader.substring(6))).split(":");
            String username = authTokens[0];
            String password = authTokens[1];

            return hasAccess(username, password, request);
        }
        return false;
    }


    private boolean hasAccess(String username, String password, BodyHttpServletRequestWrapper request) {
        if (userService.isUserExist(username, password))
            return this.handler.handle(username, password, request);
        return false;
    }
}
