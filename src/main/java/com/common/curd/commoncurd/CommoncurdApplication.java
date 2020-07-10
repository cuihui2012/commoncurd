package com.common.curd.commoncurd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.common.curd.commoncurd.dao")  //扫描mybatis下所有的mapper,可替代dao层的@Mapper注解
public class CommoncurdApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommoncurdApplication.class, args);
    }

}
