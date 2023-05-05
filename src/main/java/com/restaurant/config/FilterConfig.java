package com.restaurant.config;

import com.restaurant.controller.filter.AuthFilter;
import com.restaurant.controller.filter.auth.Handler;
import com.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final UserService userService;
    private final List<Handler> handlers;

    @Bean
    public FilterRegistrationBean<AuthFilter> customFilterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(Handler.link(handlers), userService));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
