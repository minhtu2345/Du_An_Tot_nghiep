package com.example.demo.config;

import com.example.demo.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class InterceptorConfig
        implements WebMvcConfigurer {
    @Autowired
    GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(
            InterceptorRegistry registry) {
        // TODO
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**");
    }
}
