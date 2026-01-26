package com.example.bilibili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bilibili.mapper")
public class BilibiliApplication {
    public static void main(String[] args) {
        SpringApplication.run(BilibiliApplication.class, args);
    }
}
