package com.mtw.blog_vue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mtw.blog_vue.mapper")
public class BlogVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogVueApplication.class, args);
    }

}
