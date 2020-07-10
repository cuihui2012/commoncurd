package com.common.curd.commoncurd.config;

import com.common.curd.commoncurd.interceptor.InterfaceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 交给spring管理
    // 不能直接使用new出来的对象,否则该类中的依赖注入不生效
    @Bean
    public InterfaceInterceptor interfaceInterceptor(){
        return new InterfaceInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 增加自定义拦截器
        registry.addInterceptor(interfaceInterceptor()).addPathPatterns("/**");
    }
}
