package com.common.curd.commoncurd.config;

import com.common.curd.commoncurd.interceptor.CommonAddOrUpdateInterceptor;
import com.common.curd.commoncurd.interceptor.CommonDeleteInterceptor;
import com.common.curd.commoncurd.interceptor.CommonInterceptor;
import com.common.curd.commoncurd.interceptor.CommonSelectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 交给spring管理
    // 不能直接使用new出来的对象,否则该类中的依赖注入不生效
    @Bean
    public CommonInterceptor commonInterceptor(){
        return new CommonInterceptor();
    }
    @Bean
    public CommonSelectInterceptor commonSelectInterceptor(){
        return new CommonSelectInterceptor();
    }
    @Bean
    public CommonAddOrUpdateInterceptor commonAddOrUpdateInterceptor(){
        return new CommonAddOrUpdateInterceptor();
    }
    @Bean
    public CommonDeleteInterceptor commonDeleteInterceptor(){
        return new CommonDeleteInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 增加自定义拦截器
        registry.addInterceptor(commonInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(commonSelectInterceptor()).addPathPatterns("/common/getDataByViewName");
        registry.addInterceptor(commonAddOrUpdateInterceptor()).addPathPatterns("/common/addOrUpdateDataByTableNames", "/common/addOrUpdateOrDeleteDataByTableNames");
        registry.addInterceptor(commonDeleteInterceptor()).addPathPatterns("/common/deleteDataByTableNames", "/common/addOrUpdateOrDeleteDataByTableNames");
    }
}
