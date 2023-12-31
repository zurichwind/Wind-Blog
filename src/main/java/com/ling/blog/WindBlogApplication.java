package com.ling.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.ling.blog.mapper")
@SpringBootApplication
public class WindBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindBlogApplication.class, args);
    }

}
